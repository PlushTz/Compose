package com.example.compose.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

/**
 * Desc:
 * @author lijt
 * Created on 2023/8/30 10:30
 * Email: lijt@eetrust.com
 */
@Composable
fun ComposeText1(text: String) {
    Text(
        text = text,
        style = TextStyle(color = Color.Black, fontSize = TextUnit(20f, TextUnitType.Sp))
    )
}

@Composable
fun ComposeText2(text: String) {
    Text(
        text = text, style = TextStyle(
            color = Color.Black,
            fontSize = TextUnit(22f, TextUnitType.Sp),
            fontWeight = FontWeight.Light,
            background = Color.White,
            lineHeight = 35.sp,
            letterSpacing = 4.sp,
        )
    )
}

@Composable
fun ComposeText3(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = TextUnit(22f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            background = Color.Gray,
            lineHeight = 35.sp,
            letterSpacing = 4.sp,
            textDecoration = TextDecoration.LineThrough
        )
    )
}

@Composable
fun ComposeText4(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun ComposeText5(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun ComposeText6() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 24.sp)) {
                append("通知是指")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.W900, fontSize = 24.sp)) {
                append("Android")
            }
            withStyle(style = ParagraphStyle(lineHeight = 25.sp)) {
                append("在您应用的界面之外显示的消息，旨在向用户提供提醒、来自他人的通信信息或您应用中的其他实时信息。用户可以点按通知来打开应用，或直接从通知中执行操作。")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Red
                )
            ) {
                append("agree")
            }
        },
    )
}

@Composable
fun ComposeText7(modifier: Modifier = Modifier) {
    SelectionContainer(modifier = modifier) {
        Text(text = "可复制的文字", color = Color.Blue)
    }
}