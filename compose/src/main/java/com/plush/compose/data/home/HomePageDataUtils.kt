package com.plush.compose.data.home

import com.plush.compose.router.AppRoute

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/19
 * Email: lijt@eetrust.com
 */
object HomePageDataUtils {
    val homePageDataList = listOf(
        HomePageData(title = "State", route = AppRoute.ROUTE_STATE),
        HomePageData(title = "ConversationScreen", route = AppRoute.ROUTE_CONVERSATION_SCREEN),
        HomePageData(title = "LaunchedEffect", route = AppRoute.ROUTE_EFFECT),
    )
}