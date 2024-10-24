package com.example.video2.feature.moviefavorites.domain.usecase

import com.example.video2.feature.moviefavorites.domain.repository.FavoritesRepository
import com.example.video2.feature.moviefavorites.domain.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConsumeFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    fun execute(): Flow<List<Favorite>> = repository.consumeFavorites()
}