package com.example.animelist.domain.repository

import com.example.animelist.domain.model.Movie
import com.example.animelist.domain.model.MovieDetails
import com.example.animelist.domain.model.FavoriteMovie
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun searchMovies(title: String): Single<List<Movie>>
    fun getMovieDetails(imdbID: String): Single<MovieDetails>
    fun isFavorite(imdbID: String): Flow<Boolean>
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>
    suspend fun addMovieToFavorites(imdbID: String)
    suspend fun removeMovieFromFavorites(imdbID: String)
}