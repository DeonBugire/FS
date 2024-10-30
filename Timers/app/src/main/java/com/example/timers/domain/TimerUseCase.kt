package com.example.timers.domain

import kotlinx.coroutines.flow.Flow

interface TimerUseCase {
    fun startTimer(timeInSeconds: Long): Flow<String>
    fun cancelTimer()
}
