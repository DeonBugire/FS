package com.example.animelist.data.mapper

import com.example.animelist.data.model.MovieDetailsResponse
import com.example.animelist.domain.model.MovieDetails

object MovieDetailsMapper {

    fun mapToDomain(detailsResponse: MovieDetailsResponse): MovieDetails {
        return MovieDetails(
            title = detailsResponse.title,
            year = detailsResponse.year,
            released = detailsResponse.released,
            genre = detailsResponse.genre,
            director = detailsResponse.director,
            actors = detailsResponse.actors,
            plot = detailsResponse.plot,
            poster = detailsResponse.poster
        )
    }
}