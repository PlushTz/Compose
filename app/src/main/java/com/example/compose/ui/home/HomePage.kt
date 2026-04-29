package com.example.compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.router.AppRoute

/**
 * Desc: 首页网格列表
 * @author lijt
 * Created on 2026/1/9
 */

data class HomeMenuItem(
    val title: String,
    val route: String = "",
    val icon: Painter? = null
)

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(Modifier, rememberNavController(), PaddingValues())
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val menuItems = listOf(
        HomeMenuItem(
            "抖音无水印",
            AppRoute.ROUTE_DOUYIN_VIDEO,
            painterResource(R.mipmap.icon_douyin)
        ),
//        HomeMenuItem("HorizontalPager", AppRoute.ROUTE_HORIZONTAL_PAGE),
//        HomeMenuItem("LazyColum", AppRoute.ROUTE_LAZY_COLUMN),
//        HomeMenuItem("LazyVerticalGrid", AppRoute.ROUTE_LAZY_VERTICAL_GRID),
//        HomeMenuItem("自定义组件", AppRoute.ROUTE_CUSTOM_COMPONENT),
//        HomeMenuItem("VerticalPager"),
//        HomeMenuItem("Modifier"),
//        HomeMenuItem("HorizontalPager", AppRoute.ROUTE_HORIZONTAL_PAGE),
//        HomeMenuItem("LazyColum", AppRoute.ROUTE_LAZY_COLUMN),
//        HomeMenuItem("LazyVerticalGrid", AppRoute.ROUTE_LAZY_VERTICAL_GRID),
//        HomeMenuItem("自定义组件", AppRoute.ROUTE_CUSTOM_COMPONENT),
//        HomeMenuItem("VerticalPager"),
//        HomeMenuItem("Modifier"),
    )

    LazyVerticalGrid(
        // 将 columns 修改为固定 2 列
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .consumeWindowInsets(paddingValues),
        contentPadding = paddingValues,
        // 卡片之间的间距
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { item ->
            HomeGridItem(
                item = item
            ) {
                if (item.route.isNotEmpty()) {
                    navController.navigate(item.route)
                }
            }
        }
    }
}

@Composable
fun HomeGridItem(
    item: HomeMenuItem,
    onClick: () -> Unit
) {
    // 使用 ElevatedCard 实现卡片阴影效果
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f), // 使卡片保持正方形
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.offset(y = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val icon = item.icon
                if (icon != null) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clickable(false, onClick = {}),
                        painter = icon,
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = TextUnit(16f, type = TextUnitType.Sp)
                )
            }
        }
    }
}
