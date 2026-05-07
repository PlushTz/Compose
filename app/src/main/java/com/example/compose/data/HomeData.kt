package com.example.compose.data

import com.example.compose.R
import com.example.compose.router.AppRoute

/**
 * Desc:
 * @author lijt
 * Created on 2026/5/6
 * Email: lijt@eetrust.com
 */
object HomeData {
    val data = listOf(
        HomeMenuItem("抖音无水印", AppRoute.ROUTE_DOUYIN_VIDEO, R.mipmap.icon_douyin),
        HomeMenuItem("HorizontalPager", AppRoute.ROUTE_HORIZONTAL_PAGE),
        HomeMenuItem("LazyColum", AppRoute.ROUTE_LAZY_COLUMN),
        HomeMenuItem("LazyVerticalGrid", AppRoute.ROUTE_LAZY_VERTICAL_GRID),
        HomeMenuItem("自定义组件", AppRoute.ROUTE_CUSTOM_COMPONENT),
        HomeMenuItem("VerticalPager"),
        HomeMenuItem("Modifier"),
        HomeMenuItem("HorizontalPager", AppRoute.ROUTE_HORIZONTAL_PAGE),
        HomeMenuItem("LazyColum", AppRoute.ROUTE_LAZY_COLUMN),
        HomeMenuItem("LazyVerticalGrid", AppRoute.ROUTE_LAZY_VERTICAL_GRID),
        HomeMenuItem("自定义组件", AppRoute.ROUTE_CUSTOM_COMPONENT),
        HomeMenuItem("VerticalPager"),
        HomeMenuItem("Modifier")
    )
}