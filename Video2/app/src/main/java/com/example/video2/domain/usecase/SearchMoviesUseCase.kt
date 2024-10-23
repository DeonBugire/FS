package com.example.video2.domain.usecase

import com.example.video2.domain.model.Movie
import com.example.video2.domain.repository.MovieRepository

import javax.inject.Inject
class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(title: String): List<Movie> {
        return movieRepository.searchMovies(title)
    }
}