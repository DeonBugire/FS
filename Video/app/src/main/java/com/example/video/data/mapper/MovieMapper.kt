package com.example.video.data.mapper

import com.example.video.data.model.MovieDto
import com.example.video.domain.model.Movie
object MovieMapper {
    fun mapToDomain(movieDto: MovieDto): Movie {
        return Movie(
            title = movieDto.title,
            year = movieDto.year,
            imdbID = movieDto.imdbID,
            poster = movieDto.poster
        )
    }
    fun mapToDomainList(movieDtoList: List<MovieDto>): List<Movie> {
        return movieDtoList.map { mapToDomain(it) }
    }
}