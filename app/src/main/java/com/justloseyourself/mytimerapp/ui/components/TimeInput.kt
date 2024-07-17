package com.loseyourself.mytimerapp.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.loseyourself.mytimerapp.utils.MyTimerDesignSystemSize

@Preview
@Composable
fun PreviewTimeInput() {
    TimeInput(placeholderText = "seconds", input = "123", onInputChanged = {})
}
@Composable
fun TimeInput(
    placeholderText: String,
    input: String,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = input,
        onValueChange = { newText ->
            onInputChanged(newText.filter { it.isDigit() })
        },
        placeholder = {
            Text(text = placeholderText,
                style = TextStyle(fontSize = MyTimerDesignSystemSize.timerInputFontSize)
            )},
        modifier = modifier
    )
}