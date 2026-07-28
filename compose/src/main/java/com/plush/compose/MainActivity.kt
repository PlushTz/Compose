package com.plush.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.plush.compose.ui.navhost.AppNavHost
import com.plush.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        AppContent(paddingValues = innerPadding)
                        StatusBarProtection()
                        NavigationBarProtection()
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent(paddingValues: PaddingValues) {
    AppNavHost(paddingValues)
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    ComposeTheme {
        AppContent(paddingValues = PaddingValues())
    }
}

@Composable
fun StatusBarProtection(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    val density = LocalDensity.current
    val statusBarHeightPx = WindowInsets.statusBars.getTop(density)

    if (statusBarHeightPx > 0) {
        val heightDp = with(density) { statusBarHeightPx.toDp() }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(heightDp * 1.5f) // 渐变区域稍大于状态栏高度
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.6f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
fun NavigationBarProtection(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    val density = LocalDensity.current
    val navBarHeightPx = WindowInsets.navigationBars.getBottom(density)

    if (navBarHeightPx > 0) {
        val heightDp = with(density) { navBarHeightPx.toDp() }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(heightDp * 1.5f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                color.copy(alpha = 0.4f)
                            )
                        )
                    )
            )
        }
    }
}