package com.example.compose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.router.AppRoute
import com.example.compose.theme.ComposeTheme
import com.example.compose.ui.home.ColumnsLayout
import com.example.compose.ui.horizontalpager.HorizontalPage
import com.example.compose.ui.lazycolumn.LazyColumList
import com.example.compose.ui.lazycolumn.LazyVerticalGirdList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            ComposeTheme {
                AppContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppContent() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(navController, AppRoute.ROUTE_HOME_PAGE) {
            composable(AppRoute.ROUTE_HORIZONTAL_PAGE) {
                HorizontalPage(modifier = Modifier.padding(innerPadding))
            }

            composable(AppRoute.ROUTE_HOME_PAGE) {
                ColumnsLayout(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_COLUMN) {
                LazyColumList(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_VERTICAL_GRID) {
                LazyVerticalGirdList(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
