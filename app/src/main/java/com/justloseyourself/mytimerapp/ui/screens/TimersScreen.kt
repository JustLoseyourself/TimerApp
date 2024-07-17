package com.loseyourself.mytimerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loseyourself.mytimerapp.model.domain.timer.MyTimerStateModel
import com.loseyourself.mytimerapp.utils.MyTimerDesignSystemSize
import com.loseyourself.mytimerapp.ui.components.NewTimerComponent
import com.loseyourself.mytimerapp.ui.components.TimerValueDisplay

@Preview
@Composable
fun PreviewTimers() {
    TimersScreen(PreviewTimerViewModel())
}

@Preview
@Composable
fun PreviewTimersEmpty() {
    TimersScreen(PreviewTimerViewModel().apply {
        setTimers(emptyList())
    })
}
@Composable
fun TimersScreen(timerViewModel: TimerViewModel = viewModel()) {

    val timers by timerViewModel.timers.collectAsState()

    Surface {
        Column(modifier = Modifier.padding(MyTimerDesignSystemSize.lateralPadding)) {
            NewTimerComponent { h, m, s ->
                timerViewModel.addTimer(h, m, s)
            }
            Spacer(modifier = Modifier.height(MyTimerDesignSystemSize.timerVerticalPadding))
            Text("Running timers:")
            Spacer(modifier = Modifier.height(MyTimerDesignSystemSize.timerLabelVerticalPadding))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(timers) { timer ->
                    if(timer.state == MyTimerStateModel.RUNNING){
                        Divider()
                        TimerValueDisplay(timer.formattedValue())
                    }
                }
            }
        }
    }
}