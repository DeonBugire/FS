package com.example.timers.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timers.domain.TimerUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerUseCase: TimerUseCase
) : ViewModel() {

    private val _timerLiveData = MutableLiveData<String>()
    val timerLiveData: LiveData<String> = _timerLiveData

    private var timerJob: Job? = null

    fun startTimer(timeInSeconds: Long) {
        cancelTimer()

        timerJob = viewModelScope.launch {
            try {
                timerUseCase.startTimer(timeInSeconds).collect { formattedTime ->
                    _timerLiveData.value = formattedTime
                }
            } catch (e: Exception) {
                Log.e("TimerViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
        timerUseCase.cancelTimer()
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }

    companion object {
        const val TIMER1_KEY = "Timer1"
        const val TIMER2_KEY = "Timer2"
        const val INITIAL_TIME = "00:00"
    }
}
