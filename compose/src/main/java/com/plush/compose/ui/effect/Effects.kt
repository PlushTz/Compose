package com.plush.compose.ui.effect

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/21
 * Email: lijt@eetrust.com
 */
@Composable
fun LaunchEffect01() {
    // Allow the pulse rate to be configured, so it can be sped up if the user is running
    // out of time
    var pulseRateMs by remember { mutableLongStateOf(3000L) }
    val alpha = remember { Animatable(1f) }
    LaunchedEffect(pulseRateMs) { // Restart the effect when the pulse rate changes
        while (isActive) {
            delay(pulseRateMs) // Pulse the alpha every pulseRateMs to alert the user
            alpha.animateTo(0f)
            alpha.animateTo(1f)
        }
    }
}

@Composable
fun MoviesScreen(snackbarHostState: SnackbarHostState) {

    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Button(
                onClick = {
                    // Create a new coroutine in the event handler to show a snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Something happened!")
                    }
                }
            ) {
                Text("Press me")
            }
        }
    }
}

@Composable
fun DisposableEffectExample() {
    Column(modifier = Modifier.fillMaxSize()) {
        DisposableEffect(Unit) {

            onDispose {
                Log.d("TAG", "DisposableEffect -> onDispose")
            }
        }
    }
}

@Composable
fun rememberFirebaseAnalytics(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        FirebaseAnalytics(null)
    }

    // On every successful composition, update FirebaseAnalytics with
    // the userType from the current User, ensuring that future analytics
    // events have this metadata attached
    SideEffect {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}

data class User(val userType: String) {}