package com.example.video2.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import com.example.video2.feature.moviefavorites.data.FavoritesDataSource
import com.example.video2.feature.moviefavorites.data.FavoritesDataSourceImpl
import com.example.video2.feature.moviefavorites.data.mapper.FavoriteDataMapper
import com.example.video2.feature.moviefavorites.data.repository.FavoritesRepositoryImpl
import com.example.video2.feature.moviefavorites.domain.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

// Extension to provide DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites_data_store")

@Module
class MovieFavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository {
        return favoritesRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideFavoritesDataSource(dataStore: DataStore<Preferences>): FavoritesDataSource {
        return FavoritesDataSourceImpl(dataStore)
    }
    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
    @Provides
    @Singleton
    fun provideFavoriteDataMapper(): FavoriteDataMapper {
        return FavoriteDataMapper()
    }
}