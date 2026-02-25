package com.plush.shark.video

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.video.VideoFrameDecoder
import com.plush.shark.video.ui.theme.ComposeTheme
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

data class VideoItem(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Long,
    val size: Long
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                VideoPlayerApp()
            }
        }
    }
}

@Composable
fun VideoPlayerApp() {
    var hasPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> hasPermission = isGranted }

    LaunchedEffect(Unit) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_VIDEO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        permissionLauncher.launch(permission)
    }

    if (hasPermission) {
        VideoListScreen()
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("请授予读取视频权限")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen() {
    val context = LocalContext.current
    val videos = remember { mutableStateListOf<VideoItem>() }
    var selectedVideo by remember { mutableStateOf<VideoItem?>(null) }

    LaunchedEffect(Unit) {
        videos.clear()
        videos.addAll(fetchVideos(context))
    }

    if (selectedVideo != null) {
        VideoPlayerScreen(video = selectedVideo!!) { selectedVideo = null }
    } else {
        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text("本地视频") }) }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(videos) { video ->
                    ListItem(
                        headlineContent = { Text(video.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        supportingContent = { Text("${video.duration / 1000}s | ${video.size / 1024 / 1024}MB") },
                        leadingContent = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(video.uri)
                                    .decoderFactory(VideoFrameDecoder.Factory())
                                    .build(),
                                contentDescription = "Video Thumbnail",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Gray.copy(alpha = 0.2f)),
                                contentScale = ContentScale.Crop
                            )
                        },
                        modifier = Modifier.clickable { selectedVideo = video }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerScreen(video: VideoItem, onBack: () -> Unit) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val view = LocalView.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(video.uri))
            prepare()
            playWhenReady = true
        }
    }

    var isFullScreen by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val d = exoPlayer.duration
                    duration = if (d != C.TIME_UNSET) d.coerceAtLeast(0L) else 0L
                }
            }
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                currentPosition = newPosition.positionMs
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            showSystemUI(activity, view)
        }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = exoPlayer.currentPosition
            delay(500L)
        }
    }

    BackHandler {
        if (isFullScreen) {
            isFullScreen = false
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            showSystemUI(activity, view)
        } else {
            onBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { showControls = !showControls })
            }
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    if (change.position.x < size.width / 2) {
                        val lp = activity.window.attributes
                        val currentBrightness = if (lp.screenBrightness < 0) 0.5f else lp.screenBrightness
                        lp.screenBrightness = (currentBrightness - dragAmount / size.height).coerceIn(0f, 1f)
                        activity.window.attributes = lp
                    } else {
                        val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                        val currVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                        val delta = (dragAmount / size.height * maxVol).toInt()
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (currVol - delta).coerceIn(0, maxVol), 0)
                    }
                }
            }
    ) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (showControls) {
            VideoControls(
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                duration = duration,
                isFullScreen = isFullScreen,
                onPlayPause = { if (isPlaying) exoPlayer.pause() else exoPlayer.play() },
                onSeek = { target ->
                    val videoDuration = exoPlayer.duration
                    if (videoDuration != C.TIME_UNSET && videoDuration > 0) {
                        exoPlayer.seekTo(target.coerceIn(0L, videoDuration))
                        currentPosition = exoPlayer.currentPosition
                    }
                },
                onRewind = { 
                    val target = max(0L, exoPlayer.currentPosition - 10000L)
                    exoPlayer.seekTo(target)
                    currentPosition = exoPlayer.currentPosition
                },
                onForward = { 
                    val videoDuration = exoPlayer.duration
                    if (videoDuration != C.TIME_UNSET && videoDuration > 0) {
                        val target = min(videoDuration, exoPlayer.currentPosition + 10000L)
                        exoPlayer.seekTo(target)
                        currentPosition = exoPlayer.currentPosition
                    }
                },
                onFullScreenToggle = {
                    isFullScreen = !isFullScreen
                    if (isFullScreen) {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        hideSystemUI(activity, view)
                    } else {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        showSystemUI(activity, view)
                    }
                },
                onBack = onBack
            )
        }
    }
}

@Composable
fun VideoControls(
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    isFullScreen: Boolean,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onFullScreenToggle: () -> Unit,
    onBack: () -> Unit
) {
    var isDragging by remember { mutableStateOf(false) }
    var dragPosition by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f))) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onRewind) {
                Icon(Icons.Default.Replay10, contentDescription = "Rewind", tint = Color.White, modifier = Modifier.size(48.dp))
            }
            Spacer(modifier = Modifier.width(32.dp))
            IconButton(onClick = onPlayPause, modifier = Modifier.size(64.dp)) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.width(32.dp))
            IconButton(onClick = onForward) {
                Icon(Icons.Default.Forward10, contentDescription = "Forward", tint = Color.White, modifier = Modifier.size(48.dp))
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Slider(
                value = if (isDragging) dragPosition else currentPosition.toFloat(),
                onValueChange = {
                    isDragging = true
                    dragPosition = it
                },
                onValueChangeFinished = {
                    isDragging = false
                    onSeek(dragPosition.toLong())
                },
                valueRange = 0f..max(duration.toFloat(), 1f),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val displayTime = if (isDragging) dragPosition.toLong() else currentPosition
                Text(
                    text = "${formatTime(displayTime)} / ${formatTime(duration)}",
                    color = Color.White
                )
                IconButton(onClick = onFullScreenToggle) {
                    Icon(
                        imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                        contentDescription = "FullScreen",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = max(0, ms / 1000)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

fun fetchVideos(context: Context): List<VideoItem> {
    val videos = mutableListOf<VideoItem>()
    val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }

    val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE
    )

    context.contentResolver.query(collection, projection, null, null, null)?.use { cursor ->
        val idCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
        val durCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idCol)
            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            videos.add(VideoItem(id, uri, cursor.getString(nameCol), cursor.getLong(durCol), cursor.getLong(sizeCol)))
        }
    }
    return videos
}

private fun hideSystemUI(activity: ComponentActivity, view: View) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    WindowInsetsControllerCompat(activity.window, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

private fun showSystemUI(activity: ComponentActivity, view: View) {
    WindowCompat.setDecorFitsSystemWindows(activity.window, true)
    WindowInsetsControllerCompat(activity.window, view).show(WindowInsetsCompat.Type.systemBars())
}
