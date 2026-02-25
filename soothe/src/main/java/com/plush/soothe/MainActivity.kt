package com.plush.soothe

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plush.soothe.ui.HomeScreen
import com.plush.soothe.ui.SootheBottomNavigation
import com.plush.soothe.ui.SootheNavigationRail
import com.plush.soothe.ui.theme.ComposeTheme
import com.plush.soothe.ui.uiutils.NavigationBarProtection
import com.plush.soothe.ui.uiutils.StatusBarProtection

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
            ComposeTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                SootheApp(windowSizeClass)
            }
        }
    }
}


@Composable
fun SootheAppPortrait() {
    Scaffold(bottomBar = { SootheBottomNavigation() }) { padding ->
        HomeScreen(Modifier.padding(padding))
        StatusBarProtection()
        NavigationBarProtection()
    }
}

@Composable
fun SootheAppLandscape() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Row {
            SootheNavigationRail()
            HomeScreen()
        }
    }
}

@Composable
fun SootheApp(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            SootheAppPortrait()
        }

        WindowWidthSizeClass.Expanded -> {
            SootheAppLandscape()
        }
    }
}




