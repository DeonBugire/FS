package com.example.domain.usecase

import com.example.domain.model.MovieDetails
import com.example.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    fun execute(imdbID: String): Flow<MovieDetails> {
        return repository.getMovieDetails(imdbID)
    }
}