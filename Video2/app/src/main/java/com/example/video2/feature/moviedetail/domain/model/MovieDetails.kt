package com.example.video2.feature.moviedetail.domain.model

data class MovieDetails(
    val imdbID: String,
    val title: String,
    val year: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String
)