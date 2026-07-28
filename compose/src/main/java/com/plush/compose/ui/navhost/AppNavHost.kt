package com.plush.compose.ui.navhost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plush.compose.router.AppRoute
import com.plush.compose.ui.effect.DisposableEffectExample
import com.plush.compose.ui.home.HomePage
import com.plush.compose.ui.state.StatePage
import com.plush.compose.ui.state.Test

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/19
 * Email: lijt@eetrust.com
 */
@Composable
fun AppNavHost(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoute.ROUTE_HOME_PAGE,
        modifier = Modifier.fillMaxSize() // 移除 padding，改为 fillMaxSize 覆盖全屏
    ) {
        composable(
            route = AppRoute.ROUTE_HOME_PAGE,
            content = { HomePage(navController = navController, paddingValues = paddingValues) })
        composable(
            route = AppRoute.ROUTE_STATE,
            content = { StatePage(paddingValues = paddingValues) })
        composable(route = AppRoute.ROUTE_CONVERSATION_SCREEN, content = { Test() })
        composable(route = AppRoute.ROUTE_EFFECT, content = { DisposableEffectExample() })
    }
}