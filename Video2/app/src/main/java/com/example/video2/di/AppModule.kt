package com.example.video2.di

import com.example.core.navigation.Navigator
import com.example.video2.navigation.AppNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = AppNavigator()
}