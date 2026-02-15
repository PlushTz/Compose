package com.plush.soothe

import android.os.Bundle
import androidx.activity.ComponentActivity
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

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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




