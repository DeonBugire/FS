package com.example.video2.feature.movielist.data.mapper

import com.example.video2.feature.movielist.data.model.MovieDto
import com.example.video2.feature.movielist.domain.model.Movie
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