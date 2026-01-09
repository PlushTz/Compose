package com.example.compose.ui.horizontalpager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.example.compose.data.Images
import com.example.compose.theme.Blue01
import com.example.compose.theme.Pink80
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Desc:水平滚动
 * @author lijt
 * Created on 2025/11/23
 * Email:tao351992257@163.com
 */
@Composable
fun HorizontalPage(modifier: Modifier) {
    val pageCount = Images.images.size
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    val pageSize = object : PageSize {
        override fun Density.calculateMainAxisPageSize(
            availableSpace: Int,
            pageSpacing: Int
        ): Int {
            return (availableSpace - 2 * pageSpacing) / 3
        }
    }
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )
    Column(modifier = modifier.wrapContentSize()) {
        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize(),
            state = pagerState,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp,
//            beyondViewportPageCount = 1
        ) { page ->
            Card(
                modifier = Modifier
                    .wrapContentSize()
//                    .padding(horizontal = 16.dp)
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
//                            alpha = lerp(
//                                start = 0.5f,
//                                stop = 1f,
//                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                            )
                        // 透明度动画：当前页完全不透明，滑出/滑入的页面半透明
                        val scale = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        val alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(450.dp)
                        .background(color = Pink80)
                ) {
                    AsyncImage(
                        model = Images.images[page],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Page: $page",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        // **修正**: 滚动到最后一页，索引为 pageCount - 1
                        // 防止 IndexOutOfBoundsException 崩溃
                        if (pageCount > 0) {
                            pagerState.animateScrollToPage(pageCount - 1)
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue01),
            ) {
                Text(
                    text = "Jump to Last Page (${pageCount - 1})",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 16.sp
                )
            }
        }

    }
}