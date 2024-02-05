package com.example.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        // 文本控件
                        Button(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .offset(y = 20.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                TextActivity.start(this@MainActivity)
                            },
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(text = stringResource(id = R.string.btn_text), color = Color.White)
                        }
                        // 按钮控件
                        Button(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .offset(y = 20.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                ButtonsActivity.start(this@MainActivity)
                            }, shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.btn_buttons),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}