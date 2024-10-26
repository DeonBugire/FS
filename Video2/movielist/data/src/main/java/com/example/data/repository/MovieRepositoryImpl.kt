package com.example.data.repository

import com.example.data.api.MovieApi
import com.example.data.mapper.MovieMapper
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun searchMovies(title: String): List<Movie> {
        val response = api.searchMovies(title)
        return MovieMapper.mapToDomainList(response.searchResults)
    }
}