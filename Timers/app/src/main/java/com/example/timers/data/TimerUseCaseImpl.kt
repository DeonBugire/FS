package com.example.timers.data

import com.example.timers.domain.TimerUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.takeWhile
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds

class TimerUseCaseImpl : TimerUseCase {
    private var timerJob: Job? = null

    override fun startTimer(timeInSeconds: Long): Flow<String> {
        cancelTimer()

        return flow {
            timerJob = coroutineContext[Job]
            for (elapsed in 0 until timeInSeconds) {
                emit(formatTime(timeInSeconds - elapsed))
                delay(1.seconds)
            }
            emit("00:00")
        }
            .takeWhile { it != "00:00" }
            .onCompletion { cancelTimer() }
    }

    override fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}

