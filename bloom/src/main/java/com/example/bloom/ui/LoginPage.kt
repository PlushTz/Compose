package com.example.bloom.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bloom.ui.theme.body1
import com.example.bloom.ui.theme.body2
import com.example.bloom.ui.theme.button
import com.example.bloom.ui.theme.gray
import com.example.bloom.ui.theme.h1
import com.example.bloom.ui.theme.medium
import com.example.bloom.ui.theme.pink900
import com.example.bloom.ui.theme.small
import com.example.bloom.ui.theme.white

/**
 * Desc:
 * @author lijt
 * Created on 2024/2/4
 * Email: lijt@eetrust.com
 */
@Composable
fun LoginPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
    ) {
        LoginTitle()
        LoginInputBox()
        HintWithUnderline()
        LoginButton(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginTitle() {
    Text(
        text = "Login in with email Compose",
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(top = 184.dp, bottom = 1.dp),
        style = h1,
        color = gray,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun LoginInputBox() {
    Column {
        LoginTextField(placeHolder = "Email address")
        Spacer(modifier = Modifier.height(8.dp))
        LoginTextField(placeHolder = "Password( 8 + Characters )")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(placeHolder: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(56.dp)
            .clip(small),
        placeholder = {
            Text(text = placeHolder, style = body1, color = gray)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HintWithUnderline() {
    Column(modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 16.dp)) {
        TopText()
        BottomText()
    }
}

@Composable
fun TopText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val keyWorldPre = "By Clicking below you agree to our".split(" ")
        val keyWorldPost = "and consent".split(" ")
        for (word in keyWorldPre) {
            Text(text = word, style = body2, color = gray)
        }
        Text(text = "Terms of Use", style = body2, textDecoration = TextDecoration.Underline)
        for (word in keyWorldPost) {
            Text(text = word, style = body2, color = gray)
        }
    }
}

@Composable
fun BottomText() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "to Our", style = body2, color = gray)
        Text(
            text = "Privacy Policy",
            style = body2,
            color = gray,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("home") {
                popUpTo("welcome") {
                    inclusive = true
                }
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 20.dp, end = 20.dp)
            .clip(medium), colors = ButtonDefaults.buttonColors(containerColor = pink900)
    ) {
        Text(text = "Login", style = button, color = white)
    }
}