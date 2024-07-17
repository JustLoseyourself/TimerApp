package com.loseyourself.mytimerapp.model.domain.timer

import java.util.UUID

data class TimerModel(
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val state: MyTimerStateModel = MyTimerStateModel.PAUSED,
    val id: UUID = UUID.randomUUID(),
) {
    fun formattedValue(): String = "%02d:%02d:%02d".format(hours, minutes, seconds)
}