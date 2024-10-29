package com.example.data.mapper

import com.example.data.model.MovieDetailsDto
import com.example.domain.model.MovieDetails

internal object MovieDetailsMapper {
    fun mapToDomain(imdbID: String, movieDetailsDto: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            imdbID = imdbID,
            title = movieDetailsDto.title,
            year = movieDetailsDto.year,
            director = movieDetailsDto.director,
            actors = movieDetailsDto.actors,
            plot = movieDetailsDto.plot,
            poster = movieDetailsDto.poster
        )
    }
}