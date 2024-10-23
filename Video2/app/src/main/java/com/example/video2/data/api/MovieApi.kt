package com.example.video2.data.api

import com.example.video2.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface MovieApi {
    @GET("/")
    suspend fun searchMovies(@Query("s") title: String, @Query("apikey") apiKey: String = ""): MovieResponse
}