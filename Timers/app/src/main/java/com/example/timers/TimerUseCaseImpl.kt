package com.example.timers

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class TimerUseCaseImpl : TimerUseCase {
    private var timerDisposable: Disposable? = null

    override fun startTimer(timeInSeconds: Long): Observable<String> {
        return Observable.interval(1, TimeUnit.SECONDS)
            .map { elapsedTime -> timeInSeconds - elapsedTime }
            .takeWhile { it >= 0 }
            .map { remainingTime -> formatTime(remainingTime) }
            .doOnDispose { cancelTimer() }
    }
    override fun cancelTimer() {
        timerDisposable?.dispose()
        timerDisposable = null
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
