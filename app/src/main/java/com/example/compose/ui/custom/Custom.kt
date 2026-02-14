package com.example.compose.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.theme.Black
import com.example.compose.theme.DarkGrey
import com.example.compose.theme.Orange
import com.example.compose.theme.Red01
import com.example.compose.theme.White
import com.example.compose.theme.Yellow

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/13
 * Email: lijt@eetrust.com
 */
@Composable
fun CustomComponent(modifier: Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .background(color = DarkGrey)
        ) {
            Text(
                text = "Hi here!",
                color = White,
                modifier = Modifier
                    .firstBaselineToTop(50.dp)
                    .background(color = Red01)
            )
        }
    }
}

@Composable
fun CustomComponent2(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = Black)
            .border(width = 1.dp, color = Red01, shape = RoundedCornerShape(5.dp))
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp, top = 8.dp)
                .border(5.dp, color = Orange, shape = RoundedCornerShape(5.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .padding(10.dp)
                    .wrapContentSize()
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .border(width = 1.dp, color = Yellow, shape = RoundedCornerShape(5.dp))
        ) {
            Text(text = stringResource(id = R.string.search_description), color = White)
        }
    }
}


fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }

fun Modifier.clip(shape: Shape) = graphicsLayer(shape = shape, clip = true)
