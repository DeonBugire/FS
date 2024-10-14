package ru.gb.android.homework3

import android.app.Application
import ru.gb.android.homework3.di.AppComponent

class MarketSampleApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .dataStoreModule(DataStoreModule())
            .dispatchersModule(DispatchersModule())
            .build()
    }
}
