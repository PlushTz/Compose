package com.plush.compose.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plush.compose.data.home.HomePageData
import com.plush.compose.data.home.HomePageDataUtils
import com.plush.compose.ui.theme.ComposeTheme

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/19
 * Email: lijt@eetrust.com
 */
@Preview(showBackground = true)
@Composable
fun HomaPagePreview() {
    ComposeTheme {
        HomePage(navController = rememberNavController(), paddingValues = PaddingValues())
    }
}


@Composable
fun HomePage(navController: NavController, paddingValues: PaddingValues) {
    val layoutDirection = LocalLayoutDirection.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            // 左右适配：应用系统左右边距 + 自定义 10dp
            .padding(
                start = paddingValues.calculateStartPadding(layoutDirection) + 10.dp,
                end = paddingValues.calculateEndPadding(layoutDirection) + 10.dp
            )
            .consumeWindowInsets(paddingValues)
    ) {
        // 顶部沉浸式占位 (适配状态栏)
        val topPadding = paddingValues.calculateTopPadding()
        if (topPadding > 0.dp) {
            item {
                Spacer(modifier = Modifier.height(topPadding))
            }
        }

        items(HomePageDataUtils.homePageDataList) {
            HomePageItem(it, onClick = { item ->
                if (item.route.isNotEmpty()) {
                    navController.navigate(item.route)
                }
            })
        }

        // 底部沉浸式占位 (适配导航栏)
        val bottomPadding = paddingValues.calculateBottomPadding()
        item {
            Spacer(modifier = Modifier.height(bottomPadding + 10.dp))
        }
    }
}

@Composable
fun HomePageItem(data: HomePageData, onClick: (HomePageData) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(5.dp), onClick = {
            onClick.invoke(data)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.title,
                textAlign = TextAlign.Center,
                fontSize = TextUnit(
                    16f,
                    TextUnitType.Sp
                ), fontStyle = FontStyle.Normal
            )
        }
    }
}