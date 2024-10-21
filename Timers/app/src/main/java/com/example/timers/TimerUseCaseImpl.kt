package com.example.timers

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class TimerUseCaseImpl : TimerUseCase {
    private var timerDisposable: Disposable? = null

    override fun startTimer(timeInSeconds: Long): Observable<String> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .take(timeInSeconds + 1)
            .map { elapsedTime -> formatTime(timeInSeconds - elapsedTime) }
            .doOnDispose { cancelTimer() }
    }

    override fun cancelTimer() {
        timerDisposable?.dispose()
        timerDisposable = null
    }

    private fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
