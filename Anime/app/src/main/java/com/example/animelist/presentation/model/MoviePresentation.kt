package com.example.animelist.presentation.model

data class MoviePresentation(
    val title: String,
    val poster: String,
    val imdbID: String,
    val year: String,
    val isFavorite: Boolean = false
)