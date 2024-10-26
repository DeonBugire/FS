package com.example.domain.repository

import com.example.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun consumeFavorites(): Flow<List<Favorite>>
    suspend fun addToFavorites(favorite: Favorite)
    suspend fun removeFromFavorites(favorite: Favorite)
}