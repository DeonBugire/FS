package com.example.video2.data.repository

import com.example.video2.data.api.MovieApi
import com.example.video2.data.mapper.MovieMapper
import com.example.video2.domain.model.Movie
import com.example.video2.domain.repository.MovieRepository
import javax.inject.Inject
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun searchMovies(title: String): List<Movie> {
        val response = api.searchMovies(title)
        return MovieMapper.mapToDomainList(response.searchResults)
    }
}