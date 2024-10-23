package com.example.video2.feature.moviedetail.data.mapper

import com.example.video2.feature.moviedetail.data.model.MovieDetailsDto
import com.example.video2.feature.moviedetail.domain.model.MovieDetails

object MovieDetailsMapper {
    fun mapToDomain(movieDetailsDto: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            title = movieDetailsDto.title,
            year = movieDetailsDto.year,
            director = movieDetailsDto.director,
            actors = movieDetailsDto.actors,
            plot = movieDetailsDto.plot,
            poster = movieDetailsDto.poster
        )
    }
}