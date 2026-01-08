package com.example.compose.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.compose.route.AppRoute
import com.example.compose.ui.horizontalpager.HorizontalPage
import com.example.compose.ui.theme.Blue01
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Pink80
import com.example.compose.ui.theme.Red01
import com.example.compose.ui.theme.White
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This enables edge-to-edge display. The system bars will become transparent
        // and your content can be drawn behind them.
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
        }

    }

}

@Composable
fun ColumnsLayout(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                navController.navigate(AppRoute.ROUTE_HORIZONTAL_PAGE)
            }) {
            Text(text = "HorizontalPager", color = White)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) { Text(text = "VerticalPager", color = White) }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) {
            Text(text = "Modifier", color = White)
        }
    }
}
