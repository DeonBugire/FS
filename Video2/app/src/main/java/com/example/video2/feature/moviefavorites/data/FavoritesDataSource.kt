package com.example.video2.feature.moviefavorites.data

import com.example.video2.feature.moviefavorites.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSource {
    fun consumeFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)
    suspend fun removeFavorite(favorite: FavoriteEntity)
}