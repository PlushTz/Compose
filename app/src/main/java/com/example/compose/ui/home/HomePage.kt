package com.example.compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.data.HomeData
import com.example.compose.data.HomeMenuItem

/**
 * Desc: 首页网格列表
 * @author lijt
 * Created on 2026/1/9
 */

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(Modifier, rememberNavController(), PaddingValues())
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val layoutDirection = LocalLayoutDirection.current
    Grid(
        modifier = modifier
            .fillMaxSize()
            // 左右适配：应用系统左右边距 + 自定义 10dp
            .padding(
                start = paddingValues.calculateStartPadding(layoutDirection) + 10.dp,
                end = paddingValues.calculateEndPadding(layoutDirection) + 10.dp
            )
            .consumeWindowInsets(paddingValues)
            .verticalScroll(rememberScrollState()),
        config = {
            // 使用 minmax(0.dp, 1.fr) 强制两列完全等宽
            repeat(2) { column(minmax(0.dp, 1.fr)) }
            // 设置行列间距
            gap(12.dp)
        }
    ) {
        // 顶部沉浸式占位
        val topPadding = paddingValues.calculateTopPadding()
        if (topPadding > 0.dp) {
            Spacer(
                modifier = Modifier
                    .gridItem(columnSpan = 2)
                    .height(topPadding)
            )
        }

        HomeData.data.forEach { item ->
            HomeGridItem(
                item = item
            ) {
                if (item.route.isNotEmpty()) {
                    navController.navigate(item.route)
                }
            }
        }

        // 底部沉浸式占位（适配导航栏）
        val bottomPadding = paddingValues.calculateBottomPadding()
        Spacer(
            modifier = Modifier
                .gridItem(columnSpan = 2)
                .height(bottomPadding + 10.dp)
        )
    }
}

@Composable
fun HomeGridItem(
    item: HomeMenuItem,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val icon = item.icon
            if (icon != null) {
                Image(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(alignment = Alignment.Center)
                        .clip(shape = RoundedCornerShape(20.dp)),
                    alignment = Alignment.Center,
                    painter = painterResource(icon),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontSize = TextUnit(13f, type = TextUnitType.Sp)
                )
            }
        }
    }
}
