package com.example.animelist.data.repository

import com.example.animelist.data.api.MovieApi
import com.example.animelist.data.mapper.MovieDetailsMapper
import com.example.animelist.data.mapper.MovieMapper
import com.example.animelist.domain.model.Movie
import com.example.animelist.domain.model.MovieDetails
import com.example.animelist.domain.repository.MovieRepository
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
    override fun getMovieDetails(imdbID: String): Single<MovieDetails> {
        return api.getMovieDetails(imdbID)
            .map { response ->
                MovieDetailsMapper.mapToDomain(response)
            }
    }
}
