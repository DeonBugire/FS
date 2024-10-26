package com.example.presentation.model

data class MoviePresentation(
    val title: String,
    val poster: String,
    val imdbID: String,
    var isFavorite : Boolean = false
)