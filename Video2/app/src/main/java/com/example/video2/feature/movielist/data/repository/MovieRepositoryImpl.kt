package com.example.video2.feature.movielist.data.repository

import com.example.video2.feature.movielist.data.api.MovieApi
import com.example.video2.feature.movielist.data.mapper.MovieMapper
import com.example.video2.feature.movielist.domain.model.Movie
import com.example.video2.feature.movielist.domain.repository.MovieRepository
import javax.inject.Inject
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun searchMovies(title: String): List<Movie> {
        val response = api.searchMovies(title)
        return MovieMapper.mapToDomainList(response.searchResults)
    }
}