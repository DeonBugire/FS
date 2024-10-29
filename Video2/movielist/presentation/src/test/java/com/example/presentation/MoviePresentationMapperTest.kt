package com.example.presentation

import com.example.domain.model.Favorite
import com.example.domain.model.Movie
import com.example.presentation.mapper.MoviePresentationMapper
import com.example.presentation.model.MoviePresentation
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviePresentationMapperTest {

    @Test
    fun `mapToPresentation should map Movie to MoviePresentation correctly when favorite`() {
        val movie = Movie("Guardians of the Galaxy", "2022", "id1", "posterUrl1")
        val isFavorite = true

        val result = MoviePresentationMapper.mapToPresentation(movie, isFavorite)

        val expected = MoviePresentation("Guardians of the Galaxy", "posterUrl1", "id1", true)
        assertEquals(expected, result)
    }

    @Test
    fun `mapToPresentation should map Movie to MoviePresentation correctly when not favorite`() {
        val movie = Movie("Guardians of the Galaxy", "2022", "id1", "posterUrl1")
        val isFavorite = false

        val result = MoviePresentationMapper.mapToPresentation(movie, isFavorite)

        val expected = MoviePresentation("Guardians of the Galaxy", "posterUrl1", "id1", false)
        assertEquals(expected, result)
    }

    @Test
    fun `mapToPresentationList should map a list of movies and favorites correctly`() {
        val movies = listOf(
            Movie("Guardians of the Galaxy", "2022", "id1", "posterUrl1"),
            Movie("Another Movie", "2023", "id2", "posterUrl2")
        )
        val favorites = listOf(Favorite("id1"))

        val result = MoviePresentationMapper.mapToPresentationList(movies, favorites)

        val expected = listOf(
            MoviePresentation("Guardians of the Galaxy", "posterUrl1", "id1", true),
            MoviePresentation("Another Movie", "posterUrl2", "id2", false)
        )
        assertEquals(expected, result)
    }
}