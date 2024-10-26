package com.example.data.api

import com.example.data.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsApi {
    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String = "279d0085"
    ): MovieDetailsDto
}