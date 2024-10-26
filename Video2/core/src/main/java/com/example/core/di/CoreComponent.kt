package com.example.core.di

import android.content.Context
import com.example.core.navigation.Navigator
import okhttp3.OkHttpClient
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CoreModule::class])
interface CoreComponent {
    fun okHttpClient(): OkHttpClient
    fun navigator(): Navigator

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }
}
