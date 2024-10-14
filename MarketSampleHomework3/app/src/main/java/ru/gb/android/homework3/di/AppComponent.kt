package ru.gb.android.homework3.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gb.android.homework3.MarketSampleApp
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataStoreModule::class, DispatchersModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context : Context): AppComponent
    }
    fun inject(application: MarketSampleApp)
}