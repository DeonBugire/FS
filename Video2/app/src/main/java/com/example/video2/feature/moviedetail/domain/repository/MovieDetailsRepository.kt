package com.example.video2.feature.moviedetail.domain.repository

import com.example.video2.feature.moviedetail.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetails(imdbID: String): Flow<MovieDetails>
}