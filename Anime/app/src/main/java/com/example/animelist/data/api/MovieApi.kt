package com.example.animelist.data.api

import com.example.animelist.data.model.MovieDetailsResponse
import com.example.animelist.data.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/")
    fun searchMovies(@Query("s") title: String, @Query("apikey") apiKey: String = "279d0085"): Single<MovieResponse>
    @GET("/")
    fun getMovieDetails(@Query("i") imdbID: String, @Query("apikey") apiKey: String = "b50af9f3"): Single<MovieDetailsResponse>
}
