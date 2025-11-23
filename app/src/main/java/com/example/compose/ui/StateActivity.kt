package com.example.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.compose.viewmodel.TestViewModel

/**
 * Desc:
 * @author lijt
 * Created on 2024/2/4
 * Email: lijt@eetrust.com
 */
class StateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestState4()
        }
    }
}

@Preview
@Composable
fun TestState() {
    Column(modifier = Modifier.fillMaxSize()) {
        var index = 0;
        Button(onClick = {
            index++
        }, modifier = Modifier.fillMaxWidth(), shape = ButtonDefaults.shape) {
            Text("Add")
        }

        Text(
            text = "$index",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun TestState2() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val index = remember { mutableStateOf(0) }
        Button(onClick = {
            index.value++
        }, modifier = Modifier.fillMaxWidth(), shape = ButtonDefaults.shape) {
            Text("Add")
        }

        Text(
            text = "${index.value}",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TestState3() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val index = rememberSaveable { mutableStateOf(0) }
        Button(onClick = {
            index.value++
        }, modifier = Modifier.fillMaxWidth(), shape = ButtonDefaults.shape) {
            Text("Add")
        }

        Text(
            text = "${index.value}",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TestState4() {
    val index = rememberSaveable { mutableStateOf(0) }
    TestState4(index.value) {
        index.value = it
    }
}

@Composable
fun TestState4(index: Int, onIndexChanged: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            onIndexChanged(index + 1)
        }, modifier = Modifier.fillMaxWidth(), shape = ButtonDefaults.shape) {
            Text("Add")
        }

        Text(
            text = "$index",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TestState5(testViewModel: TestViewModel) {
    val index by testViewModel.index.observeAsState()
}