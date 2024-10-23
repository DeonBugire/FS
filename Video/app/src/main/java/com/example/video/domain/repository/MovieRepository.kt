package com.example.video.domain.repository

import com.example.video.domain.model.Movie
import io.reactivex.rxjava3.core.Single
interface MovieRepository {
    fun searchMovies(title: String): Single<List<Movie>>
}