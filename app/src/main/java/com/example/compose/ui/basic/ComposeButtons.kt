package com.example.compose.ui.basic

import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable

/**
 * Desc:
 * @author lijt
 * Created on 2023/11/22
 * Email: lijt@eetrust.com
 */
@Composable
fun RadioButtonExample() {
    RadioButton(selected = true, onClick = { })
}

@Composable
fun CheckBoxExample(onCheckedChange: (Boolean) -> Unit) {
    Checkbox(checked = true, onCheckedChange = onCheckedChange)
}