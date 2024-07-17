package com.loseyourself.mytimerapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.loseyourself.mytimerapp.utils.MyTimerDesignSystemSize

@Preview
@Composable
fun PreviewTimer() {
    TimerValueDisplay("03:23:14")
}

@Composable
fun TimerValueDisplay(value: String) {
    Text(
        modifier = Modifier.padding(vertical = MyTimerDesignSystemSize.lateralPadding),
        text = value,
        style = TextStyle(fontSize = MyTimerDesignSystemSize.fontSize, fontWeight = FontWeight.Medium))
}