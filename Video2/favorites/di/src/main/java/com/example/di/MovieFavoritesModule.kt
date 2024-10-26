package com.example.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.FavoritesDataSource
import com.example.data.FavoritesDataSourceImpl
import com.example.data.mapper.FavoriteDataMapper
import com.example.data.repository.FavoritesRepositoryImpl
import com.example.domain.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites_data_store")

@Module
class MovieFavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository = favoritesRepositoryImpl

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideFavoritesDataSource(dataStore: DataStore<Preferences>): FavoritesDataSource {
        return FavoritesDataSourceImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideFavoriteDataMapper(): FavoriteDataMapper = FavoriteDataMapper()
}

