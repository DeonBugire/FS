package com.example.video2.feature.movielist.presentation.mapper

import com.example.video2.feature.movielist.domain.model.Movie
import com.example.video2.feature.movielist.presentation.model.MoviePresentation
object MoviePresentationMapper {
    private fun mapToPresentation(movie: Movie): MoviePresentation {
        return MoviePresentation(
            title = movie.title,
            poster = movie.poster,
            imdbID = movie.imdbID
        )
    }
    fun mapToPresentationList(movieList: List<Movie>): List<MoviePresentation> {
        return movieList.map { mapToPresentation(it) }
    }
}