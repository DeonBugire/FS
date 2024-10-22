package com.example.animelist.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private val Context.favoritesDataStore by preferencesDataStore(name = "favorites")

@Module
class FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesDataStore(context: Context): androidx.datastore.core.DataStore<Preferences> {
        return context.favoritesDataStore
    }
}

