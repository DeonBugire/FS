package com.example.animelist.domain.repository

import com.example.animelist.domain.model.Movie
import com.example.animelist.domain.model.MovieDetails
import io.reactivex.rxjava3.core.Single

interface MovieRepository {
    fun searchMovies(title: String): Single<List<Movie>>
    fun getMovieDetails(imdbID: String): Single<MovieDetails>
}
