package com.plush.soothe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plush.soothe.R
import com.plush.soothe.ui.theme.ComposeTheme

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/15
 * Email:tao351992257@163.com
 */
val favoriteCollectionsData = mutableListOf(
    SootheData(R.drawable.fc1_short_mantras, R.string.fc1_short_mantras),
    SootheData(R.drawable.fc2_nature_meditations, R.string.fc2_nature_meditations),
    SootheData(R.drawable.fc3_stress_and_anxiety, R.string.fc3_stress_and_anxiety),
    SootheData(R.drawable.fc4_self_massage, R.string.fc4_self_massage),
    SootheData(R.drawable.fc5_overwhelmed, R.string.fc5_overwhelmed),
    SootheData(R.drawable.fc6_nightly_wind_down, R.string.fc6_nightly_wind_down)
)

@Composable
fun FavoriteCollectionsGrid(modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = modifier.height(168.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favoriteCollectionsData) { item ->
            FavoriteCollectionCard(
                drawable = item.drawable,
                text = item.text,
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteCollectionsGridPreview() {
    ComposeTheme {
        FavoriteCollectionsGrid()
    }
}