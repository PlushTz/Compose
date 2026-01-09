package com.example.compose.ui.lazycolumn

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.compose.ui.main.Images

/**
 * Desc:
 * @author lijt
 * Created on 2025/12/8
 * Email: lijt@eetrust.com
 */
@Composable
fun LazyColumList(navController: NavController, modifier: Modifier) {

    val images = remember { Images.images }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 10.dp)
    ) {
        items(images.size) { index ->

            Card() {
                AsyncImage(
                    model = Images.images[index],
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}