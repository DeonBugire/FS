package com.example.timers

import dagger.Module
import dagger.Provides

@Module
class TimerModule {

    @Provides
    fun provideTimerUseCase(): TimerUseCase {
        return TimerUseCaseImpl()
    }

    @Provides
    fun provideViewModelFactory(timerUseCase: TimerUseCase): TimerViewModelFactory {
        return TimerViewModelFactory(timerUseCase)
    }
}