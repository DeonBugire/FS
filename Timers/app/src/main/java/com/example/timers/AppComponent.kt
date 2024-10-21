package com.example.timers

import dagger.Component

@Component(modules = [TimerModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}