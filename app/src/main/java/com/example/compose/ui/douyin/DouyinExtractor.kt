package com.example.compose.ui.douyin

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream
import java.net.URI
import java.net.URLDecoder
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DouyinExtractor @Inject constructor() {
    companion object {
        private const val TAG = "DouyinExtractor"
    }

    private val client = OkHttpClient.Builder()
        .followRedirects(true)
        .build()

    private val redirectClient = OkHttpClient.Builder()
        .followRedirects(false)
        .build()

    interface DownloadCallback {
        fun onProgress(progress: Int)
        fun onSuccess(uri: Uri?)
        fun onFailure(e: Exception)
    }

    fun extractVideoUrl(text: String, callback: (String?) -> Unit) {
        val url = findUrl(text)
        if (url == null) {
            Log.w(TAG, "No URL found in input text")
            callback(null)
            return
        }

        Log.d(TAG, "Found URL: $url")
        Thread {
            try {
                val resolvedUrl = resolveRedirect(url)
                Log.d(TAG, "Resolved video URL: $resolvedUrl")
                callback(resolvedUrl)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to extract video URL", e)
                callback(null)
            }
        }.start()
    }

    fun downloadVideo(context: Context, videoUrl: String, callback: DownloadCallback) {
        Log.d(TAG, "Starting download from: $videoUrl")
        Thread {
            var videoUri: Uri? = null
            try {
                val request = Request.Builder()
                    .url(videoUrl)
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
                    )
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw Exception("HTTP ${response.code}: ${response.message}")
                    }

                    val body = response.body ?: throw Exception("Empty response body")
                    val contentLength = body.contentLength()
                    val fileName = "Douyin_${System.currentTimeMillis()}.mp4"

                    Log.d(TAG, "Content length: $contentLength bytes")

                    val values = ContentValues().apply {
                        put(MediaStore.Video.Media.DISPLAY_NAME, fileName)
                        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            put(
                                MediaStore.Video.Media.RELATIVE_PATH,
                                Environment.DIRECTORY_DCIM + "/Camera"
                            )
                            put(MediaStore.Video.Media.IS_PENDING, 1)
                        }
                    }

                    videoUri = context.contentResolver.insert(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                        ?: throw Exception("Failed to create MediaStore entry")

                    Log.d(TAG, "Created MediaStore entry: $videoUri")

                    context.contentResolver.openOutputStream(videoUri).use { outputStream ->
                        if (outputStream == null) throw Exception("Failed to open output stream")

                        val inputStream = body.byteStream()
                        val buffer = ByteArray(8192)
                        var bytesRead: Int
                        var totalBytesRead = 0L

                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                            totalBytesRead += bytesRead
                            if (contentLength > 0) {
                                val progress = (totalBytesRead * 100 / contentLength).toInt()
                                callback.onProgress(progress)
                            }
                        }
                        outputStream.flush()
                        Log.d(TAG, "Download completed: $totalBytesRead bytes")
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val updateValues = ContentValues().apply {
                            put(MediaStore.Video.Media.IS_PENDING, 0)
                        }
                        context.contentResolver.update(videoUri!!, updateValues, null, null)
                    }

                    callback.onSuccess(videoUri)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Download failed", e)
                // Clean up the entry if download failed
                videoUri?.let { uri ->
                    try {
                        context.contentResolver.delete(uri, null, null)
                    } catch (ignore: Exception) {
                    }
                }
                callback.onFailure(e)
            }
        }.start()
    }

    private fun findUrl(text: String): String? {
        val pattern = Pattern.compile("https?://[[\\w\\-._~%?!#\\[\\]@!$&'()*+,;=]|/]+")
        val matcher = pattern.matcher(text)
        return if (matcher.find()) {
            matcher.group()
        } else {
            null
        }
    }

    private fun resolveRedirect(url: String): String {
        Log.d(TAG, "Resolving redirect for: $url")
        var currentUrl = url
        var connectionCount = 0

        // 第一步：跟踪重定向到最终页面
        while (connectionCount < 5) {
            val request = Request.Builder()
                .url(currentUrl)
                .header(
                    "User-Agent",
                    "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36"
                )
                .build()

            redirectClient.newCall(request).execute().use { response ->
                val location = response.header("Location")
                if (location != null) {
                    currentUrl = if (location.startsWith("http")) location else {
                        val uri = URI(currentUrl)
                        uri.resolve(location).toString()
                    }
                    Log.d(TAG, "Redirect #$connectionCount -> $currentUrl")
                    connectionCount++
                } else {
                    // 重定向结束，开始解析页面内容
                    Log.d(TAG, "Final page URL: $currentUrl")
                    return extractVideoUrlFromPage(currentUrl)
                }
            }
        }
        return currentUrl
    }

    /**
     * 从抖音页面中提取真实的视频下载URL
     */
    private fun extractVideoUrlFromPage(pageUrl: String): String {
        Log.d(TAG, "Extracting video URL from page: $pageUrl")
        val request = Request.Builder()
            .url(pageUrl)
            .header(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36"
            )
            .header(
                "Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
            )
            .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Failed to fetch page: ${response.code}")
            }

            val html = response.body?.string() ?: throw Exception("Empty response body")
            Log.d(TAG, "Page HTML length: ${html.length}")

            // 尝试多种方式提取视频URL
            return extractVideoUrlFromHtml(html)
                ?: throw Exception("Failed to extract video URL from page")
        }
    }

    /**
     * 从HTML内容中提取视频URL
     */
    private fun extractVideoUrlFromHtml(html: String): String? {
        // 方法1: 从 __ROUTER_DATA__ 中提取
        extractFromRouterData(html)?.let {
            Log.d(TAG, "Extracted from __ROUTER_DATA__: $it")
            return it
        }

        // 方法2: 从 render_data 中提取
        extractFromRenderData(html)?.let {
            Log.d(TAG, "Extracted from render_data: $it")
            return it
        }

        // 方法3: 从 script 标签中的 JSON 数据提取
        extractFromScriptTag(html)?.let {
            Log.d(TAG, "Extracted from script tag: $it")
            return it
        }

        // 方法4: 使用正则表达式直接匹配视频URL
        extractByRegex(html)?.let {
            Log.d(TAG, "Extracted by regex: $it")
            return it
        }

        Log.w(TAG, "All extraction methods failed")
        return null
    }

    /**
     * 从 __ROUTER_DATA__ 中提取视频URL
     */
    private fun extractFromRouterData(html: String): String? {
        return try {
            // 修改正则表达式：去掉末尾分号要求，使用贪婪匹配
            val pattern =
                Pattern.compile("""window\._ROUTER_DATA\s*=\s*(\{.+\})\s*""", Pattern.DOTALL)
            val matcher = pattern.matcher(html)
            if (matcher.find()) {
                val rawJsonStr = matcher.group(1) ?: return null
                // 先解码Unicode转义，再解析JSON
                val jsonStr = decodeUnicode(rawJsonStr)
                Log.d(TAG, "Found _ROUTER_DATA, length: ${jsonStr.length}")
                val json = JSONObject(jsonStr)

                // 尝试从 loaderData -> video_(id)/page -> videoInfoRes -> item_list[0] -> video -> play_addr 路径提取
                val loaderData = json.optJSONObject("loaderData")
                Log.d(TAG, "loaderData: ${loaderData != null}")

                // 列出所有键
                if (loaderData != null) {
                    val keys = mutableListOf<String>()
                    val iterator = loaderData.keys()
                    while (iterator.hasNext()) {
                        keys.add(iterator.next())
                    }
                    Log.d(TAG, "loaderData keys: $keys")
                }

                // 尝试不同的键名（Unicode解码后应该是 video_(id)/page）
                var videoPage = loaderData?.optJSONObject("video_(id)/page")
                Log.d(TAG, "video_(id)/page: ${videoPage != null}")

                if (videoPage == null) {
                    // 尝试遍历loaderData的键，找到包含video的键
                    if (loaderData != null) {
                        val keyIterator = loaderData.keys()
                        while (keyIterator.hasNext()) {
                            val key = keyIterator.next()
                            if (key.contains("video") && key.contains("page")) {
                                videoPage = loaderData.optJSONObject(key)
                                Log.d(
                                    TAG,
                                    "Found video page with key: $key, value: ${videoPage != null}"
                                )
                                if (videoPage != null) break
                            }
                        }
                    }
                }

                val videoInfoRes = videoPage?.optJSONObject("videoInfoRes")
                Log.d(TAG, "videoInfoRes: ${videoInfoRes != null}")

                val itemList = videoInfoRes?.optJSONArray("item_list")
                Log.d(TAG, "itemList size: ${itemList?.length() ?: 0}")

                if (itemList != null && itemList.length() > 0) {
                    val firstItem = itemList.optJSONObject(0)
                    val video = firstItem?.optJSONObject("video")
                    Log.d(TAG, "video object: ${video != null}")

                    // 优先使用 play_addr_265 / play_addr_h264（无水印）
                    // 其次使用 play_addr 并去掉水印
                    var playAddr = video?.optJSONObject("play_addr_265")
                    Log.d(TAG, "play_addr_265: ${playAddr != null}")

                    if (playAddr == null) {
                        playAddr = video?.optJSONObject("play_addr_h264")
                        Log.d(TAG, "play_addr_h264: ${playAddr != null}")
                    }

                    if (playAddr == null) {
                        playAddr = video?.optJSONObject("play_addr")
                        Log.d(TAG, "play_addr: ${playAddr != null}")
                    }

                    val urlList = playAddr?.optJSONArray("url_list")
                    Log.d(TAG, "url_list size: ${urlList?.length() ?: 0}")

                    if (urlList != null && urlList.length() > 0) {
                        val videoUrl = urlList.optString(0)
                        Log.d(TAG, "Raw video URL: $videoUrl")
                        if (videoUrl.isNotEmpty()) {
                            // 处理Unicode编码的URL
                            var decodedUrl = decodeUnicode(videoUrl)
                            // 去掉水印：将 playwm 替换为 play
                            if (decodedUrl.contains("playwm")) {
                                decodedUrl = decodedUrl.replace("playwm", "play")
                                Log.d(TAG, "Removed watermark, URL: $decodedUrl")
                            }
                            Log.d(TAG, "Final video URL: $decodedUrl")
                            if (decodedUrl.startsWith("http")) {
                                return decodedUrl
                            }
                        }
                    }
                }

                // 备用路径：尝试其他可能的JSON结构
                val videoUrl = json.optJSONObject("videoData")
                    ?.optString("playAddr")
                    ?: json.optJSONObject("video")
                        ?.optString("play_addr")
                    ?: json.optJSONObject("videoData")
                        ?.optJSONObject("video")
                        ?.optString("play_addr")

                if (!videoUrl.isNullOrEmpty() && videoUrl.startsWith("http")) {
                    return videoUrl
                }
            } else {
                Log.w(TAG, "_ROUTER_DATA not found in HTML")
            }
            null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to extract from __ROUTER_DATA__", e)
            null
        }
    }

    /**
     * 解码Unicode字符串
     */
    private fun decodeUnicode(str: String): String {
        return try {
            // 处理 \u002F 这样的Unicode转义序列
            val pattern = Pattern.compile("""\\u([0-9a-fA-F]{4})""")
            val matcher = pattern.matcher(str)
            val sb = StringBuffer()
            while (matcher.find()) {
                val codePoint = matcher.group(1)?.toInt(16) ?: 0
                matcher.appendReplacement(sb, Character.toString(codePoint.toChar()))
            }
            matcher.appendTail(sb)
            sb.toString()
        } catch (e: Exception) {
            str
        }
    }

    /**
     * 从 render_data 中提取视频URL
     */
    private fun extractFromRenderData(html: String): String? {
        return try {
            val pattern = Pattern.compile("""render_data\s*=\s*\[([^\]]+)\]""")
            val matcher = pattern.matcher(html)
            if (matcher.find()) {
                val jsonStr = URLDecoder.decode(matcher.group(1), "UTF-8")
                val json = JSONObject(jsonStr)

                val videoUrl = json.optJSONObject("video")
                    ?.optString("play_addr")
                    ?: json.optJSONObject("videoData")
                        ?.optString("playAddr")

                if (!videoUrl.isNullOrEmpty() && videoUrl.startsWith("http")) {
                    return videoUrl
                }
            }
            null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to extract from render_data", e)
            null
        }
    }

    /**
     * 从 script 标签中提取视频URL
     */
    private fun extractFromScriptTag(html: String): String? {
        return try {
            // 匹配包含视频URL的script标签
            val pattern = Pattern.compile(
                """<script[^>]*>.*?"play_addr"\s*:\s*"([^"]+)".*?</script>""",
                Pattern.DOTALL
            )
            val matcher = pattern.matcher(html)
            if (matcher.find()) {
                val videoUrl = matcher.group(1) ?: return null
                if (videoUrl.startsWith("http")) {
                    return videoUrl
                }
            }
            null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to extract from script tag", e)
            null
        }
    }

    /**
     * 使用正则表达式直接匹配视频URL
     */
    private fun extractByRegex(html: String): String? {
        return try {
            // 匹配常见的视频URL格式
            val patterns = listOf(
                Pattern.compile("""https?://[^"'\s]+\.douyinvod\.com/[^"'\s]+\.mp4[^"'\s]*"""),
                Pattern.compile("""https?://[^"'\s]+v\d+-[^"'\s]+\.douyinvod\.com/[^"'\s]+"""),
                Pattern.compile(""""playAddr"\s*:\s*"(https?://[^"]+)""""),
                Pattern.compile(""""play_addr"\s*:\s*"(https?://[^"]+)"""")
            )

            for (pattern in patterns) {
                val matcher = pattern.matcher(html)
                if (matcher.find()) {
                    return matcher.group(1) ?: matcher.group()
                }
            }
            null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to extract by regex", e)
            null
        }
    }
}