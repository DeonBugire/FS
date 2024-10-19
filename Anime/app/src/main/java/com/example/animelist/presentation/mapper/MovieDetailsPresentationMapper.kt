package com.example.animelist.presentation.mapper

import com.example.animelist.domain.model.MovieDetails
import com.example.animelist.presentation.model.MovieDetailsPresentation

object MovieDetailsPresentationMapper {

    fun mapToPresentation(movieDetails: MovieDetails): MovieDetailsPresentation {
        return MovieDetailsPresentation(
            title = movieDetails.title,
            year = movieDetails.year,
            released = movieDetails.released,
            genre = movieDetails.genre,
            director = movieDetails.director,
            actors = movieDetails.actors,
            plot = movieDetails.plot,
            poster = movieDetails.poster
        )
    }
}