package com.example.data.api

import com.example.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface MovieApi {
    @GET("/")
    suspend fun searchMovies(@Query("s") title: String, @Query("apikey") apiKey: String = "279d0085"): MovieResponse
}