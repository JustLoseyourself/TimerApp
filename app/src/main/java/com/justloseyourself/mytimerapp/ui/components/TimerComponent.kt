package com.loseyourself.mytimerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.loseyourself.mytimerapp.utils.MyTimerDesignSystemSize

@Preview
@Composable
fun PreviewNewTimerComponent() {
    NewTimerComponent { h, m, s ->

    }
}
@Composable
fun NewTimerComponent(onAddTimer: (Int, Int, Int) -> Unit) {
    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MyTimerDesignSystemSize.horizontalSpacing)) {
        TimeInput(placeholderText = "hours",
            modifier = Modifier.weight(1f),
            input = hours,
            onInputChanged = { hours = it })
        TimeInput(placeholderText = "minutes",
            modifier = Modifier.weight(1f),
            input = minutes,
            onInputChanged = { minutes = it })
        TimeInput(placeholderText = "seconds",
            modifier = Modifier.weight(1f),
            input = seconds,
            onInputChanged = { seconds = it })
        Button(
            onClick = {
                onAddTimer(hours.toIntOrNull() ?: 0, minutes.toIntOrNull() ?: 0, seconds.toIntOrNull() ?: 0)
            },
            modifier = Modifier.weight(1f)
        ) {
            Text("Start!")
        }
    }
}