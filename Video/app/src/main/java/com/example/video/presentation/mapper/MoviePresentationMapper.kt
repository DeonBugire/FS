package com.example.video.presentation.mapper

import com.example.video.domain.model.Movie
import com.example.video.presentation.model.MoviePresentation
object MoviePresentationMapper {
    private fun mapToPresentation(movie: Movie): MoviePresentation {
        return MoviePresentation(
            title = movie.title,
            poster = movie.poster
        )
    }
    fun mapToPresentationList(movieList: List<Movie>): List<MoviePresentation> {
        return movieList.map { mapToPresentation(it) }
    }
}