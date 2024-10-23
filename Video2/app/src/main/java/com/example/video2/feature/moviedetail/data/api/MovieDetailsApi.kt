package com.example.video2.feature.moviedetail.data.api

import com.example.video2.feature.moviedetail.data.model.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsApi {
    @GET("/")
    suspend fun getMovieDetails(@Query("i") imdbID: String, @Query("apikey") apiKey: String = "279d0085"): MovieDetailsDto
}