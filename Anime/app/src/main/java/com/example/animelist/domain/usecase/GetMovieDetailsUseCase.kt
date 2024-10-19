package com.example.animelist.domain.usecase

import com.example.animelist.domain.repository.MovieRepository
import com.example.animelist.presentation.mapper.MovieDetailsPresentationMapper
import com.example.animelist.presentation.model.MovieDetailsPresentation
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(imdbID: String): Single<MovieDetailsPresentation> {
        return movieRepository.getMovieDetails(imdbID)
            .map { movieDetails ->
                MovieDetailsPresentationMapper.mapToPresentation(movieDetails)
            }
    }
}
