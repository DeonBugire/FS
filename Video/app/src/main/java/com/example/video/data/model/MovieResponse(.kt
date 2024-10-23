package com.example.video.data.model

import com.google.gson.annotations.SerializedName
data class MovieResponse(
    @SerializedName("Search") val searchResults: List<MovieDto>,
    @SerializedName("Response") val response: String
)
data class MovieDto(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Poster") val poster: String
)