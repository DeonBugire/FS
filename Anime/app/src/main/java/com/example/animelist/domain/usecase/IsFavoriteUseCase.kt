package com.example.animelist.domain.usecase

import com.example.animelist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(imdbID: String): Flow<Boolean> {
        return movieRepository.isFavorite(imdbID)
    }
}