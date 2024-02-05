package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloom.ui.HomePage
import com.example.bloom.ui.LoginPage
import com.example.bloom.ui.WelcomePage
import com.example.bloom.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomePage(navController)
                    }
                    composable("login") {
                        LoginPage(navController)
                    }
                    composable("home") {
                        HomePage()
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePagePreview() {
    ComposeTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    ComposeTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    ComposeTheme {
        HomePage()
    }
}