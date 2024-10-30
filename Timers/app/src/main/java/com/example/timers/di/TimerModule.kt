package com.example.timers.di

import com.example.timers.data.TimerUseCaseImpl
import com.example.timers.domain.TimerUseCase
import com.example.timers.presentation.factory.TimerViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TimerModule {

    @Provides
    fun provideTimerUseCase(): TimerUseCase {
        return TimerUseCaseImpl()
    }

    @Provides
    fun provideViewModelFactory(): TimerViewModelFactory {
        return TimerViewModelFactory()
    }
}
