package com.example.video.data.repository

import com.example.video.data.api.MovieApi
import com.example.video.data.mapper.MovieMapper
import com.example.video.domain.model.Movie
import com.example.video.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override fun searchMovies(title: String): Single<List<Movie>> {
        return api.searchMovies(title)
            .map { response ->
                MovieMapper.mapToDomainList(response.searchResults)
            }
    }
}