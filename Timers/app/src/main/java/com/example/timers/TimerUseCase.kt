package com.example.timers

import io.reactivex.rxjava3.core.Observable

interface TimerUseCase {
    fun startTimer(timeInSeconds: Long): Observable<String>
    fun cancelTimer()
}