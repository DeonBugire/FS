package com.example.video.data.api

import com.example.video.data.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
interface MovieApi {
    @GET("/")
    fun searchMovies(@Query("s") title: String, @Query("apikey") apiKey: String = ""): Single<MovieResponse>
}