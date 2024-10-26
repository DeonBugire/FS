package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(title: String): List<Movie> {
        return try {
            movieRepository.searchMovies(title)
        } catch (e: Exception) {
            emptyList()
        }
    }
}