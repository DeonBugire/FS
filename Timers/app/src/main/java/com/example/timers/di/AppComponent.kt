package com.example.timers.di

import com.example.timers.MainActivity
import dagger.Component

@Component(modules = [TimerModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}