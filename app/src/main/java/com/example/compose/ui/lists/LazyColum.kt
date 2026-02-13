package com.example.compose.ui.lists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background)
    ) {

        contactGrouped.forEach { (initial, contacts) ->
            stickyHeader {
                StickyHeader(initial)
            }

            items(contacts) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun StickyHeader(content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onBackground)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = contact.avatar,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface
        )
    }
}