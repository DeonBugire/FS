package com.example.video2.feature.movielist.presentation.model

data class MoviePresentation(
    val title: String,
    val poster: String,
    val imdbID: String,
    var isFavorite : Boolean = false
)