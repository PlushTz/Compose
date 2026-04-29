package com.example.compose.ui.navhost

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.router.AppRoute
import com.example.compose.ui.custom.CustomComponent3
import com.example.compose.ui.douyin.DouyinExtractorPage
import com.example.compose.ui.grids.LazyVerticalGirdList
import com.example.compose.ui.home.HomePage
import com.example.compose.ui.horizontalpager.HorizontalPage
import com.example.compose.ui.lists.LazyColumList

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/25
 * Email: lijt@eetrust.com
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = AppRoute.ROUTE_HOME_PAGE,
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
        ) {
            composable(AppRoute.ROUTE_HORIZONTAL_PAGE) {
                HorizontalPage(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            composable(AppRoute.ROUTE_HOME_PAGE) {
                HomePage(
                    navController = navController,
                    modifier = Modifier,
                    paddingValues = paddingValues,
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_COLUMN) {
                LazyColumList(
                    navController = navController,
                    modifier = Modifier,
                    paddingValues = paddingValues
                )
            }
            composable(route = AppRoute.ROUTE_LAZY_VERTICAL_GRID) {
                LazyVerticalGirdList(
                    navController = navController,
                    modifier = Modifier,
                    paddingValues = paddingValues
                )
            }

            composable(route = AppRoute.ROUTE_CUSTOM_COMPONENT) {
                CustomComponent3(
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = AppRoute.ROUTE_DOUYIN_VIDEO) {
                DouyinExtractorPage(
                    navController = navController,
                    paddingValues = paddingValues,
                )
            }
        }
    }
}