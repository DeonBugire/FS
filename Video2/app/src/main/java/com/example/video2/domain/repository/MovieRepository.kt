package com.example.video2.domain.repository

import com.example.video2.domain.model.Movie

interface MovieRepository {
    suspend fun searchMovies(title: String): List<Movie>
}