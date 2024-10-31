package com.example.timers.di

import com.example.timers.data.TimerUseCaseImpl
import com.example.timers.domain.TimerUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class TimerBindingModule {
    @Binds
    abstract fun bindTimerUseCase(timerUseCaseImpl: TimerUseCaseImpl): TimerUseCase
}
