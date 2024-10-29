package com.example.video2

import android.app.Application
import com.example.video2.di.AppComponent
import com.example.video2.di.DaggerAppComponent
import com.example.core.di.DependenciesProvider
import com.example.core.di.Dependencies

internal class VideoApp : Application(), DependenciesProvider {
   val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun getDependencies(): Dependencies = appComponent
}