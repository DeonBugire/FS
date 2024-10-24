package com.example.video2.feature.moviedetail.presentation.mapper

import com.example.video2.feature.moviedetail.domain.model.MovieDetails
import com.example.video2.feature.moviedetail.presentation.model.MovieDetailsPresentation

object MovieDetailsPresentationMapper {
    fun mapToPresentation(movieDetails: MovieDetails, isFavorite: Boolean): MovieDetailsPresentation {
        return MovieDetailsPresentation(
            imdbID = movieDetails.imdbID,
            title = movieDetails.title,
            year = movieDetails.year,
            director = movieDetails.director,
            actors = movieDetails.actors,
            plot = movieDetails.plot,
            poster = movieDetails.poster,
            isFavorite = isFavorite
        )
    }
}

