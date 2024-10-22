package com.example.animelist.domain.usecase

import com.example.animelist.domain.repository.MovieRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(imdbID: String) {
        movieRepository.removeMovieFromFavorites(imdbID)
    }
}