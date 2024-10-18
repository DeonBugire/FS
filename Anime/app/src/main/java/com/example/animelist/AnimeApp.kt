package com.example.animelist

import android.app.Application
import com.example.animelist.di.DaggerAppComponent

class AnimeApp : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}