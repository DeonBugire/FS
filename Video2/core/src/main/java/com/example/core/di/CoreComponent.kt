package com.example.core.di

import android.content.Context
import okhttp3.OkHttpClient
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface CoreComponent {
    fun okHttpClient(): OkHttpClient

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }
}
