package com.example.domain.usecase

import com.example.domain.repository.FavoritesRepository
import com.example.domain.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConsumeFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    fun execute(): Flow<List<Favorite>> = repository.consumeFavorites()
}