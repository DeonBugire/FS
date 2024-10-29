package com.example.domain

import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRepository = mock(MovieRepository::class.java)
        searchMoviesUseCase = SearchMoviesUseCase(movieRepository)
    }

    @Test
    fun `execute should return movie list when repository returns results`() = runBlocking {
        // Given
        val title = "Inception"
        val movies = listOf(Movie("1", "Inception", "2010", "url1"))
        `when`(movieRepository.searchMovies(title)).thenReturn(movies)

        // When
        val result = searchMoviesUseCase.execute(title)

        // Then
        assertEquals(movies, result)
        verify(movieRepository).searchMovies(title)
        verifyNoMoreInteractions(movieRepository)
    }

    @Test
    fun `execute should return empty list when repository throws an exception`() = runBlocking {
        // Given
        val title = "Unknown"
        `when`(movieRepository.searchMovies(title)).thenThrow(RuntimeException("Network error"))

        // When
        val result = searchMoviesUseCase.execute(title)

        // Then
        assertEquals(emptyList<Movie>(), result)
        verify(movieRepository).searchMovies(title)
        verifyNoMoreInteractions(movieRepository)
    }
}