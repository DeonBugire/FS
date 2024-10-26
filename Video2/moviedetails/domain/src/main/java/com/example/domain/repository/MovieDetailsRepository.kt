package com.example.domain.repository

import com.example.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetails(imdbID: String): Flow<MovieDetails>
}