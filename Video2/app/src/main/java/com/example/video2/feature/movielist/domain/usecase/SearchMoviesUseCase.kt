package com.example.video2.feature.movielist.domain.usecase

import com.example.video2.feature.movielist.domain.model.Movie
import com.example.video2.feature.movielist.domain.repository.MovieRepository
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