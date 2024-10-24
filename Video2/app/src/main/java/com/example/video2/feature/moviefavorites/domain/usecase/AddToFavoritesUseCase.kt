package com.example.video2.feature.moviefavorites.domain.usecase

import com.example.video2.feature.moviefavorites.domain.repository.FavoritesRepository
import com.example.video2.feature.moviefavorites.domain.model.Favorite
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend fun execute(favorite: Favorite) = repository.addToFavorites(favorite)
}