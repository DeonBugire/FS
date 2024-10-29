package com.example.presentation

import com.example.presentation.mapper.MovieDetailsPresentationMapper
import com.example.domain.model.Favorite
import com.example.domain.model.MovieDetails
import com.example.presentation.model.MovieDetailsPresentation
import com.example.domain.usecase.AddToFavoritesUseCase
import com.example.domain.usecase.ConsumeFavoritesUseCase
import com.example.domain.usecase.GetMovieDetailsUseCase
import com.example.domain.usecase.RemoveFromFavoritesUseCase
import com.example.presentation.viewmodel.MovieDetailsViewModel
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    private lateinit var consumeFavoritesUseCase: ConsumeFavoritesUseCase
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase
    private lateinit var removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
    private lateinit var viewModel: MovieDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        consumeFavoritesUseCase = mockk()
        getMovieDetailsUseCase = mockk()
        addToFavoritesUseCase = mockk(relaxed = true)
        removeFromFavoritesUseCase = mockk(relaxed = true)
        viewModel = MovieDetailsViewModel(
            consumeFavoritesUseCase,
            getMovieDetailsUseCase,
            addToFavoritesUseCase,
            removeFromFavoritesUseCase
        )
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetails should update movieDetails with data from use cases`() = runTest {
        val imdbID = "tt1234567"
        val movieDetails = MovieDetails(
            imdbID = imdbID,
            title = "Example Movie",
            year = "2022",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "Example plot.",
            poster = "http://example.com/poster.jpg"
        )
        val favoriteList = listOf(Favorite(imdbID))

        coEvery { consumeFavoritesUseCase.execute() } returns flowOf(favoriteList)
        coEvery { getMovieDetailsUseCase.execute(imdbID) } returns flowOf(movieDetails)

        viewModel.getMovieDetails(imdbID)
        advanceUntilIdle()

        val expectedPresentation = MovieDetailsPresentationMapper.mapToPresentation(movieDetails, isFavorite = true)
        assertEquals(expectedPresentation, viewModel.movieDetails.value)
    }

    @Test
    fun `toggleFavorite should add to favorites when movie is not favorite`() = runTest {
        val movieDetails = MovieDetailsPresentation(
            imdbID = "tt1234567",
            title = "Example Movie",
            year = "2022",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "Example plot.",
            poster = "http://example.com/poster.jpg",
            isFavorite = false
        )

        viewModel.toggleFavorite(movieDetails)
        advanceUntilIdle()

        coVerify { addToFavoritesUseCase.execute(Favorite(movieDetails.imdbID)) }
        assertEquals(true, viewModel.movieDetails.value?.isFavorite)
    }

    @Test
    fun `toggleFavorite should remove from favorites when movie is already favorite`() = runTest {
        val movieDetails = MovieDetailsPresentation(
            imdbID = "tt1234567",
            title = "Example Movie",
            year = "2022",
            director = "Jane Doe",
            actors = "John Doe, Jane Smith",
            plot = "Example plot.",
            poster = "http://example.com/poster.jpg",
            isFavorite = true
        )

        viewModel.toggleFavorite(movieDetails)
        advanceUntilIdle()

        coVerify { removeFromFavoritesUseCase.execute(Favorite(movieDetails.imdbID)) }
        assertEquals(false, viewModel.movieDetails.value?.isFavorite)
    }
}
