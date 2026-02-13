package com.example.compose.ui.grids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.compose.data.Images

/**
 * Desc:
 * @author lijt
 * Created on 2026/1/9
 * Email: lijt@eetrust.com
 */
@Composable
fun LazyVerticalGirdList(navController: NavController, modifier: Modifier) {
    val images = remember { Images.images }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        modifier = modifier.padding(start = 5.dp, end = 5.dp)
    ) {
        items(images.size) { index ->
            AsyncImage(
                model = Images.images[index],
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}