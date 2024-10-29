package com.example.presentation.mapper

import com.example.domain.model.Favorite
import com.example.domain.model.Movie
import com.example.presentation.model.MoviePresentation

internal object MoviePresentationMapper {
    internal fun mapToPresentation(movie: Movie, isFavorite: Boolean): MoviePresentation {
        return MoviePresentation(
            title = movie.title,
            poster = movie.poster,
            imdbID = movie.imdbID,
            isFavorite = isFavorite
        )
    }

    fun mapToPresentationList(
        movieList: List<Movie>,
        favorites: List<Favorite>
    ): List<MoviePresentation> {
        return movieList.map { movie ->
            val isFavorite = favorites.any { it.id == movie.imdbID }
            mapToPresentation(movie, isFavorite)
        }
    }
}
