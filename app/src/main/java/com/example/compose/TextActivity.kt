package com.example.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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

                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color(0xFFD3D3D3))
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SearchBar(modifier: Modifier) {
        var text by remember {
            mutableStateOf("")
        }
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search_description)
                        )
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(Color.White, CircleShape)
                    .height(30.dp)
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    fun DecorateTextField() {
        var text by rememberSaveable {
            mutableStateOf("init")
        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = TextStyle(color = Color.White),
                cursorBrush = SolidColor(Color.Blue),
                decorationBox = { innerTextField -> // decorationBox内部负责编写输入框样式
                    Row(
                        Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .background(Color.Blue, RoundedCornerShape(percent = 30))
                            .padding(1.dp)
                            .background(Color.Green, RoundedCornerShape(percent = 29)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, tint = Color.White, contentDescription = null)
                        Spacer(Modifier.width(5.dp))
                        Box(modifier = Modifier.padding(top = 7.dp, bottom = 7.dp, end = 7.dp)) {
                            innerTextField() // 自定义样式这行代码是关键，没有这一行输入文字后无法展示，光标也看不到
                        }
                    }
                }
            )
        }
    }
}