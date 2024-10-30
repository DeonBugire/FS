package com.example.timers.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timers.data.TimerUseCaseImpl
import com.example.timers.presentation.vm.TimerViewModel
import javax.inject.Inject

class TimerViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(TimerUseCaseImpl()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}