package com.plush.soothe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plush.soothe.R

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/15
 * Email:tao351992257@163.com
 */
data class SootheData(val drawable: Int, val text: Int)

val alignYourBodyData = mutableListOf(
    SootheData(R.drawable.ab1_inversions, R.string.ab1_inversions),
    SootheData(R.drawable.ab2_quick_yoga, R.string.ab2_quick_yoga),
    SootheData(R.drawable.ab3_stretching, R.string.ab3_stretching),
    SootheData(R.drawable.ab4_tabata, R.string.ab4_tabata),
    SootheData(R.drawable.ab5_hiit, R.string.ab5_hiit),
    SootheData(R.drawable.ab6_pre_natal_yoga, R.string.ab6_pre_natal_yoga)
)

@Composable
fun AlignYourBodyRow(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(alignYourBodyData) { item ->
            AlignBodyElement(drawable = item.drawable, text = item.text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlignYourBodyRowPreview() {
    AlignYourBodyRow()
}