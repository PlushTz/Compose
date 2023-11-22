package com.example.compose.ui.basic

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.theme.Blue01
import com.example.compose.ui.theme.PurpleGrey80
import com.example.compose.ui.theme.Red01

/**
 * Desc:
 * @author lijt
 * Created on 2023/11/17
 * Email: lijt@eetrust.com
 */

@Composable
fun TextButtonExample(modifier: Modifier = Modifier, invoke: () -> Unit) {
    TextButton(
        modifier = modifier,
        onClick = { invoke.invoke() },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Blue01,
            contentColor = Color.Gray
        ),
    ) {
        Text(text = "点击显示AlertDialog", color = Color.White)
    }
}

@Composable
fun ButtonExample(modifier: Modifier = Modifier, invoke: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { invoke.invoke() },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue01,
            contentColor = Color.DarkGray
        )
    ) {
        Text(text = "按钮", color = Color.White, modifier = modifier)
    }
}

@Composable
fun AlertDialogExample(modifier: Modifier = Modifier, openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        AlertDialog(
            containerColor = PurpleGrey80,
            modifier = modifier,
            onDismissRequest = {
                openDialog.value = false
            }, confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue01,
                        contentColor = Color.Gray
                    )
                ) {
                    Text(text = stringResource(id = R.string.agree), color = Color.White)
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red01,
                        contentColor = Color.DarkGray
                    )
                ) {
                    Text(text = stringResource(id = R.string.reject), color = Color.White)
                }
            }, title = {
                Text(text = "开启位置服务")
            }, text = { Text(text = "这将意味着，我们会给您提供精准的位置服务") },
            shape = RoundedCornerShape(5.dp)
        )
    }
}