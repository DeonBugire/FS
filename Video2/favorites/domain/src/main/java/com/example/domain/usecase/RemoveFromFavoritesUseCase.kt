package com.example.domain.usecase

import com.example.domain.repository.FavoritesRepository
import com.example.domain.model.Favorite
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend fun execute(favorite: Favorite) = repository.removeFromFavorites(favorite)
}