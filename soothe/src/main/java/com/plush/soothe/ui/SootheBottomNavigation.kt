package com.plush.soothe.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plush.soothe.R
import com.plush.soothe.ui.theme.ComposeTheme

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/15
 * Email:tao351992257@163.com
 */
@Composable
fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(R.string.bottom_navigation_home)) },
            selected = true,
            onClick = {})
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = { Text(text = stringResource(R.string.bottom_navigation_profile)) },
            selected = false,
            onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SootheBottomNavigationPreview() {
    ComposeTheme {
        SootheBottomNavigation()
    }
}