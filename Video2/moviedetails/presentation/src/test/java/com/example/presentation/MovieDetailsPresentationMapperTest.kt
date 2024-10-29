package com.example.presentation

import com.example.presentation.mapper.MovieDetailsPresentationMapper
import com.example.domain.model.MovieDetails
import com.example.presentation.model.MovieDetailsPresentation
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsPresentationMapperTest {

    @Test
    fun `mapToPresentation should map MovieDetails to MovieDetailsPresentation with correct fields`() {
        val movieDetails = MovieDetails(
            imdbID = "tt1234567",
            title = "Example Movie",
            year = "2022",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "Example plot.",
            poster = "http://example.com/poster.jpg"
        )
        val isFavorite = true

        val result = MovieDetailsPresentationMapper.mapToPresentation(movieDetails, isFavorite)

        val expected = MovieDetailsPresentation(
            imdbID = "tt1234567",
            title = "Example Movie",
            year = "2022",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "Example plot.",
            poster = "http://example.com/poster.jpg",
            isFavorite = true
        )

        assertEquals(expected, result)
    }
}