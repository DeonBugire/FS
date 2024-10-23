package com.example.video.domain.usecase

import com.example.video.domain.model.Movie
import com.example.video.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(title: String): Single<List<Movie>> {
        return movieRepository.searchMovies(title)
    }
}