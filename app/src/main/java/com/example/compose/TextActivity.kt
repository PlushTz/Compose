package com.example.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

/**
 * Desc:
 * @author lijt
 * Created on 2023/11/22
 * Email: lijt@eetrust.com
 */
class TextActivity : ComponentActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TextActivity::class.java))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        var username by remember { mutableStateOf("") }
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                                .offset(y = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            value = username,
                            onValueChange = {
                                username = it
                            },
                            label = { Text(text = "输入框", color = Color.Black) },
                            placeholder = {
                                Text(text = "输入用户名", color = Color.Gray)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            })

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .align(Alignment.CenterHorizontally),
                            value = username,
                            onValueChange = {
                                username = it
                            }, label = { Text(text = "OutlinedTextField") },
                            placeholder = { Text(text = "输入用户名") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = null
                                )
                            })
                        var text by remember { mutableStateOf("") }
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp),
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            decorationBox = { innerTextField ->
                                Column {
                                    innerTextField()
                                    // 设置分割线
                                    Divider(
                                        thickness = 2.dp, // 分割线的粗度
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = Color.Black)
                                    )
                                }
                            })

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = text,
                                onValueChange = {
                                    text = it
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}