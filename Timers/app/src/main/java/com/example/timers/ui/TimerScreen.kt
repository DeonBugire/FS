package com.example.timers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timers.presentation.vm.TimerViewModel

@Composable
fun TimerScreen(viewModel1: TimerViewModel, viewModel2: TimerViewModel) {
    val showSeconds = remember { mutableStateOf(true) }

    val timeState1 = viewModel1.timerLiveData.observeAsState(TimerViewModel.INITIAL_TIME)
    val timeState2 = viewModel2.timerLiveData.observeAsState(TimerViewModel.INITIAL_TIME)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Show seconds")
            Switch(
                checked = showSeconds.value,
                onCheckedChange = { showSeconds.value = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TimerDisplay(
            title = "Timer 1",
            time = timeState1.value,
            showSeconds = showSeconds.value,
            onStart = { viewModel1.startTimer(5 * 60) },
            onCancel = { viewModel1.cancelTimer() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TimerDisplay(
            title = "Timer 2",
            time = timeState2.value,
            showSeconds = showSeconds.value,
            onStart = { viewModel2.startTimer(3 * 60) },
            onCancel = { viewModel2.cancelTimer() }
        )
    }
}