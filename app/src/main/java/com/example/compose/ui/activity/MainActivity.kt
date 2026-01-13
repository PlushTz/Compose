package com.example.compose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.RectRulers
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.layout.innermostOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.router.AppRoute
import com.example.compose.theme.ComposeTheme
import com.example.compose.ui.home.ColumnsLayout
import com.example.compose.ui.horizontalpager.HorizontalPage
import com.example.compose.ui.list_and_grids.LazyColumList
import com.example.compose.ui.list_and_grids.LazyVerticalGirdList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(
//                lightScrim = android.graphics.Color.TRANSPARENT,
//                darkScrim = android.graphics.Color.TRANSPARENT
//            ),
//            navigationBarStyle = SystemBarStyle.auto(
//                lightScrim = android.graphics.Color.TRANSPARENT,
//                darkScrim = android.graphics.Color.TRANSPARENT
//            )
        )
        setContent {
            ComposeTheme(dynamicColor = false) {
                AppContent()
//                StatusBarProtection()
            }
        }
    }
}

@Preview
@Composable
fun AppContent() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) { innerPadding ->
        NavHost(navController, AppRoute.ROUTE_HOME_PAGE) {
            composable(AppRoute.ROUTE_HORIZONTAL_PAGE) {
                HorizontalPage(modifier = Modifier.consumeWindowInsets(innerPadding))
            }

            composable(AppRoute.ROUTE_HOME_PAGE) {
                ColumnsLayout(
                    modifier = Modifier.consumeWindowInsets(innerPadding),
                    navController = navController
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_COLUMN) {
                LazyColumList(
                    navController = navController,
                    modifier = Modifier.consumeWindowInsets(innerPadding)
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_VERTICAL_GRID) {
                LazyVerticalGirdList(
                    navController = navController,
                    modifier = Modifier.consumeWindowInsets(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun StatusBarProtection(
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    heightProvider: () -> Float = calculateGradientHeight(),
) {

    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()
        val gradient = Brush.verticalGradient(
            colors = listOf(
                color.copy(alpha = 1f),
                color.copy(alpha = .8f),
                Color.Transparent
            ),
            startY = 0f,
            endY = calculatedHeight
        )
        drawRect(
            brush = gradient,
            size = Size(size.width, calculatedHeight),
        )
    }
}

@Composable
fun calculateGradientHeight(): () -> Float {
    val statusBars = WindowInsets.statusBars
    val density = LocalDensity.current
    return { statusBars.getTop(density).times(1.2f) }
}
