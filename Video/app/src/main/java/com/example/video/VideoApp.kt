package com.example.video

import android.app.Application
import com.example.video.di.DaggerAppComponent

class VideoApp : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}