package com.example.animelist.presentation.mapper

import com.example.animelist.domain.model.FavoriteMovie
import com.example.animelist.domain.model.Movie
import com.example.animelist.presentation.model.MoviePresentation

object MoviePresentationMapper {

    fun mapToPresentation(movie: Movie, favoriteEntities: List<FavoriteMovie>): MoviePresentation {
        val isFavorite = favoriteEntities.any { it.imdbID == movie.imdbID }
        return MoviePresentation(
            title = movie.title,
            poster = movie.poster,
            imdbID = movie.imdbID,
            year = movie.year,
            isFavorite = isFavorite
        )
    }

    fun mapToPresentationList(movieList: List<Movie>, favoriteEntities: List<FavoriteMovie>): List<MoviePresentation> {
        return movieList.map { movie -> mapToPresentation(movie, favoriteEntities) }
    }
}
