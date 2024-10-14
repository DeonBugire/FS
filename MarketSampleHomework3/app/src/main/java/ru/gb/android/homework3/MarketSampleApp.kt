package ru.gb.android.homework3

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import ru.gb.android.homework3.di.AppComponent
import ru.gb.android.homework3.di.DaggerAppComponent

class MarketSampleApp : Application() {
    init {
        instance = this
    }

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(context = applicationContext)
    }
    companion object {
        private var instance: MarketSampleApp? = null
        fun getInstance(): MarketSampleApp = instance!!
    }
}
