package com.example.video2.feature.moviedetail.presentation.model

data class MovieDetailsPresentation(
    val imdbID: String,
    val title: String,
    val year: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    var isFavorite: Boolean = false
)