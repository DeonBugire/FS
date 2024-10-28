package com.example.core.di

import android.content.Context
import okhttp3.OkHttpClient
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [NetworkModule::class])
interface CoreComponent {
    fun okHttpClient(): OkHttpClient

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }
}
