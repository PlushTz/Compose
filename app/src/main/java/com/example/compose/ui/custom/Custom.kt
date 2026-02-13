package com.example.compose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.theme.DarkGrey
import com.example.compose.theme.Red01
import com.example.compose.theme.White

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
        Column(modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(color = DarkGrey)) {
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
