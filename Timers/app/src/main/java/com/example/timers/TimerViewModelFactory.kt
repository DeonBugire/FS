package com.example.timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class TimerViewModelFactory @Inject constructor(
    private val timerUseCase: TimerUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(timerUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
