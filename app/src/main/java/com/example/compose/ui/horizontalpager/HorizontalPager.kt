package com.example.compose.ui.horizontalpager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.example.compose.ui.main.Images
import com.example.compose.ui.theme.Blue01
import com.example.compose.ui.theme.Pink80
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Desc:水平滚动
 * @author lijt
 * Created on 2025/11/23
 * Email:tao351992257@163.com
 */
@Preview
@Composable
fun HorizontalPage(modifier: Modifier? = null) {
    val pagerState = rememberPagerState(pageCount = { 5 })
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
    Column {
        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = Color.White),
            state = pagerState,
//            pageSize = PageSize.Fixed(200.dp),
            beyondViewportPageCount = 10,
            flingBehavior = fling
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .size(width = 300.dp, height = 500.dp)
                        .align(Alignment.Center)
                        .graphicsLayer {
                            val pageOffset =
                                ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Pink80)
                    ) {
                        AsyncImage(
                            model = Images.images[page],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Page: $page",
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Pink80)
        ) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(containerColor = Blue01), onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(5)
                    }
                }, shape = RoundedCornerShape(5.dp)
            ) {
                Text("Jump to Page 5")
            }
        }

    }
}