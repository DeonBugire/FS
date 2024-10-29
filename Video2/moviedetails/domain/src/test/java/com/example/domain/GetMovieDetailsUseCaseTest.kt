package com.example.domain

import com.example.domain.model.MovieDetails
import com.example.domain.repository.MovieDetailsRepository
import com.example.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*

class GetMovieDetailsUseCaseTest {

    private lateinit var repository: MovieDetailsRepository
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        repository = mock(MovieDetailsRepository::class.java)
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `execute should return movie details from repository`() = runBlocking {
        val imdbID = "movie123"
        val movieDetails = MovieDetails(
            imdbID = imdbID,
            title = "Example Movie",
            year = "2021",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "An example movie plot.",
            poster = "http://example.com/poster.jpg"
        )
        `when`(repository.getMovieDetails(imdbID)).thenReturn(flowOf(movieDetails))

        val result = getMovieDetailsUseCase.execute(imdbID).toList()

        assertEquals(listOf(movieDetails), result)
        verify(repository).getMovieDetails(imdbID)
        verifyNoMoreInteractions(repository)
    }
}