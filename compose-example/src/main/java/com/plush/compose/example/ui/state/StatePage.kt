package com.plush.compose.example.ui.state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/19
 * Email: lijt@eetrust.com
 */
@Preview(showBackground = true)
@Composable
fun StatePagePreview() {
    StatePage(paddingValues = PaddingValues())
}

val stateViewModel = StateViewModel(SavedStateHandle())

@Composable
fun StatePage(paddingValues: PaddingValues) {

//    val count = rememberSaveable { mutableIntStateOf(0) }
//
//    var name by rememberSaveable { mutableStateOf("") }

    StateExample(
        paddingValues = paddingValues,
        name = stateViewModel.textField.name,
        count = stateViewModel.textField.count,
        onIncrement = { stateViewModel.onIncrement() }, onChange = {
            stateViewModel.onValueChange(it)
        })
}

@Composable
fun StateExample(
    paddingValues: PaddingValues,
    name: String,
    count: Int,
    onIncrement: () -> Unit,
    onChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)
                .align(Alignment.CenterHorizontally),
            value = name,
            onValueChange = {
                onChange.invoke(it)
            },
            label = { Text("Name") },
        )

        Text(text = "Count: $count")
        Button(onClick = { onIncrement.invoke() }) {
            Text(text = "Increment")
        }
    }
}

class StateViewModel(saveStateHandle: SavedStateHandle) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
//    var textField by saveStateHandle.saveable { mutableStateOf(TextField("", 0)) }
    var textField by saveStateHandle.saveable(
        "textField",
        init = { mutableStateOf(TextField("", 0)) }
    )

    fun onValueChange(value: String) {
        textField = textField.copy(name = value)
    }

    fun onIncrement() {
        textField = textField.copy(count = textField.count + 1)
    }
}

data class TextField(val name: String, val count: Int)

