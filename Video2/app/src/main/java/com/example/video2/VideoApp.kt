package com.example.video2

import android.app.Application
import com.example.video2.di.DaggerAppComponent

class VideoApp : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}