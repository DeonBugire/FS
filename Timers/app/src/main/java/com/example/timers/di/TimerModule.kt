package com.example.timers.di

import com.example.timers.presentation.factory.TimerViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TimerModule {

    @Provides
    fun provideViewModelFactory(): TimerViewModelFactory {
        return TimerViewModelFactory()
    }
}