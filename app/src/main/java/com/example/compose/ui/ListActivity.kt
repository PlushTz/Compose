package com.example.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.ui.theme.ComposeTheme

/**
 * Desc:
 * @author lijt
 * Created on 2023/11/22
 * Email: lijt@eetrust.com
 */
class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {

            }
        }
    }
}