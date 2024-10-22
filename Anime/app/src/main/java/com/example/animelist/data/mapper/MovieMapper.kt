package com.example.animelist.data.mapper

import com.example.animelist.data.model.MovieDto
import com.example.animelist.domain.model.Movie

object MovieMapper {
    fun mapToDomain(movieDto: MovieDto): Movie {
        return Movie(
            imdbID = movieDto.imdbID,
            title = movieDto.title,
            poster = movieDto.poster,
            year = movieDto.year
        )
    }

    fun mapToDomainList(movieDtoList: List<MovieDto>): List<Movie> {
        return movieDtoList.map { mapToDomain(it) }
    }
}
