package com.example.compose.ui.lazycolumn

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.compose.data.Images

/**
 * Desc:
 * @author lijt
 * Created on 2025/12/8
 * Email: lijt@eetrust.com
 */
data class Contact(val name: String, val avatar: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyColumList(navController: NavController, modifier: Modifier) {
    val contactGrouped = remember {
        val names = listOf(
            "Alice", "Aria", "Bob", "Bella", "Charlie", "Cathy",
            "David", "Daisy", "Eve", "Elena", "Frank", "Fiona",
            "George", "Grace", "Henry", "Holly"
        )
        names.mapIndexed { index, name ->
            Contact(name, Images.images[index % Images.images.size])
        }.groupBy { it.name.first().toString() }.toSortedMap()
    }
    val images = remember { Images.images }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {

        contactGrouped.forEach { (initial, contacts) ->
            stickyHeader {
                Text(
                    text = initial,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(contacts) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = contact.avatar,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}