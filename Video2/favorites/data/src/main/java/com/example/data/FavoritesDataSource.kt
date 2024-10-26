package com.example.data

import com.example.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSource {
    fun consumeFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)
    suspend fun removeFavorite(favorite: FavoriteEntity)
}