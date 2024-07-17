package com.loseyourself.mytimerapp.ui.screens

import com.loseyourself.mytimerapp.model.domain.timer.MyTimerStateModel
import com.loseyourself.mytimerapp.model.domain.timer.TimerModel

class PreviewTimerViewModel: TimerViewModel() {
    init {
        _timers.value = listOf(
            TimerModel(3, 23, 14, MyTimerStateModel.RUNNING),
            TimerModel(0, 0, 11, MyTimerStateModel.RUNNING)
        )
    }

    fun setTimers(timers: List<TimerModel>) {
        _timers.value = timers
    }
}