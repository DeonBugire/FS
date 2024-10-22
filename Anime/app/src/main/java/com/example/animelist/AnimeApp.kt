package com.example.animelist

import android.app.Application
import com.example.animelist.di.AppComponent
import com.example.animelist.di.DaggerAppComponent

class AnimeApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}