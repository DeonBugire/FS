package com.example.data.repository

import com.example.data.mapper.MovieDetailsMapper
import com.example.data.api.MovieDetailsApi
import com.example.domain.model.MovieDetails
import com.example.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val api: MovieDetailsApi
) : MovieDetailsRepository {
    override fun getMovieDetails(imdbID: String): Flow<MovieDetails> {
        return flow {
            val response = api.getMovieDetails(imdbID)
            emit(MovieDetailsMapper.mapToDomain(imdbID, response))
        }
    }
}
