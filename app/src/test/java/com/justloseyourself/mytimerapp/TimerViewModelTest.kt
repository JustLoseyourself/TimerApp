package com.justloseyourself.mytimerapp

import com.loseyourself.mytimerapp.model.domain.timer.MyTimerStateModel
import com.loseyourself.mytimerapp.model.domain.timer.TimerModel
import com.loseyourself.mytimerapp.ui.screens.TimerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModelTest {
    private lateinit var viewModel: TimerViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TimerViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `timer initialized correctly`() = runTest {
        viewModel.addTimer(-1, -1, -1)
        viewModel.addTimer(0, 0, 0)
        viewModel.addTimer(1, 0, 0)
        val addedTimer = viewModel.timers.value.first()
        assertEquals(1, addedTimer.hours)
        assertEquals(0, addedTimer.minutes)
        assertEquals(0, addedTimer.seconds)
    }

    @Test
    fun `addTimer increases list size`() = runTest {
        viewModel.addTimer(1, 0, 0)
        assertEquals(1, viewModel.timers.value.size)
    }

    @Test
    fun `formattedValue returns correct format`() {
        val timerModel = TimerModel(1, 2, 3, MyTimerStateModel.PAUSED)
        assertEquals("01:02:03", timerModel.formattedValue())
    }

    @Test
    fun `startTimer updates timer state to RUNNING`() = runTest {
        viewModel.addTimer(0, 0, 10)
        val timerUpdated = viewModel.timers.value.first()
        assertEquals(MyTimerStateModel.RUNNING, timerUpdated.state)
    }
}