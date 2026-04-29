package com.example.compose.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.ComposeTheme
import com.example.compose.ui.navhost.AppNavHost
import com.example.compose.ui.uiutils.NavigationBarProtection
import com.example.compose.ui.uiutils.StatusBarProtection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 开启 Edge-to-Edge，配置状态栏和导航栏样式
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

        // 2. 禁用 Android 10+ 导航栏的强制对比度遮罩，实现真正的沉浸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            ComposeTheme(dynamicColor = false) {
                AppContent()
            }
        }
    }
}

@Preview
@Composable
fun AppContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppNavHost(innerPadding)
            // 状态栏渐变
            StatusBarProtection()
            // 导航栏渐变
            NavigationBarProtection()
        }
    }
}
