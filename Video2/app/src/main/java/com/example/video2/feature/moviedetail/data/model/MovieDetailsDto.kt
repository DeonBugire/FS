package com.example.video2.feature.moviedetail.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Poster") val poster: String
)