package com.example.animelist.domain.usecase

import com.example.animelist.domain.model.FavoriteMovie
import com.example.animelist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(): Flow<List<FavoriteMovie>> {
        return movieRepository.getFavoriteMovies()
    }
}
