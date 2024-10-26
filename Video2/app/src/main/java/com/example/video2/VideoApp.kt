package com.example.video2

import android.app.Application
import com.example.core.di.CoreComponent
import com.example.core.di.DaggerCoreComponent
import com.example.video2.di.AppComponent
import com.example.video2.di.DaggerAppComponent

class VideoApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        val coreComponent: CoreComponent = DaggerCoreComponent.factory().create(applicationContext)
        appComponent = DaggerAppComponent.factory().create(applicationContext, coreComponent)
    }
}