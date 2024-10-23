package com.example.video2.feature.movielist.domain.repository

import com.example.video2.feature.movielist.domain.model.Movie

interface MovieRepository {
    suspend fun searchMovies(title: String): List<Movie>
}