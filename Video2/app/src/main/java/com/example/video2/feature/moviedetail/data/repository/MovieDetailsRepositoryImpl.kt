package com.example.video2.feature.moviedetail.data.repository

import com.example.video2.feature.moviedetail.data.mapper.MovieDetailsMapper
import com.example.video2.feature.moviedetail.domain.model.MovieDetails
import com.example.video2.feature.moviedetail.domain.repository.MovieDetailsRepository
import com.example.video2.feature.moviedetail.data.api.MovieDetailsApi
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
