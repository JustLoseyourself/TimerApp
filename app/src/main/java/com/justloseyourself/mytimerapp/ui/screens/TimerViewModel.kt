package com.loseyourself.mytimerapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loseyourself.mytimerapp.model.domain.timer.MyTimerStateModel
import com.loseyourself.mytimerapp.model.domain.timer.TimerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
open class TimerViewModel @Inject constructor() : ViewModel() {
    protected val _timers = MutableStateFlow<List<TimerModel>>(emptyList())
    val timers: StateFlow<List<TimerModel>> = _timers
    private val mutex = Mutex()

    private fun calculateNormalizedTime(hours: Int, minutes: Int, seconds: Int): Triple<Int, Int, Int> {
        val validSeconds = if (seconds < 0) 0 else seconds
        val validMinutes = if (minutes < 0) 0 else minutes
        val validHours = if (hours < 0) 0 else hours

        val additionalMinutesFromSeconds = validSeconds / 60
        val secondsRollover = validSeconds % 60

        val totalMinutes = validMinutes + additionalMinutesFromSeconds
        val additionalHoursFromMinutes = totalMinutes / 60
        val minutesRollover = totalMinutes % 60

        val totalHours = validHours + additionalHoursFromMinutes

        return Triple(totalHours, minutesRollover, secondsRollover)
    }

    fun addTimer(hours: Int, minutes: Int, seconds: Int) = viewModelScope.launch(Dispatchers.IO) {
        val (totalHours, finalMinutes, finalSeconds) = calculateNormalizedTime(hours, minutes, seconds)
        if (totalHours == 0 && finalMinutes == 0 && finalSeconds == 0) {
            return@launch
        }

        val newTimer = TimerModel(hours = totalHours, minutes = finalMinutes, seconds = finalSeconds)
        mutex.withLock {
            _timers.value = _timers.value + listOf(newTimer)
        }
        startTimer(newTimer)
    }

    fun startTimer(timerModel: TimerModel) = viewModelScope.launch(Dispatchers.IO) {
        mutex.withLock {
            _timers.value.find { it.id == timerModel.id }?.let {
                _timers.value = _timers.value.map { if (it.id == timerModel.id) it.copy(state = MyTimerStateModel.RUNNING) else it }
            }
        }
        countdownTimer(timerModel)
    }

    private fun countdownTimer(timerModel: TimerModel) = viewModelScope.launch(Dispatchers.IO) {
        var timeInSeconds = timerModel.hours * 3600 + timerModel.minutes * 60 + timerModel.seconds
        while (timeInSeconds > 0) {
            delay(1000)
            mutex.withLock {
                _timers.value.find { it.id == timerModel.id }?.let {
                    if (it.state == MyTimerStateModel.RUNNING) {
                        timeInSeconds--
                        _timers.value = _timers.value.map { if (it.id == timerModel.id) it.copy(
                            hours = timeInSeconds / 3600,
                            minutes = (timeInSeconds % 3600) / 60,
                            seconds = timeInSeconds % 60
                        ) else it }
                    }
                }
            }
        }
        if (timeInSeconds == 0) {
            stopTimer(timerModel)
        }
    }

    private fun stopTimer(timerModel: TimerModel) = viewModelScope.launch(Dispatchers.IO) {
        mutex.withLock {
            _timers.value = _timers.value.map { if (it.id == timerModel.id) it.copy(state = MyTimerStateModel.STOPPED) else it }
        }
    }
}