package com.example.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.model.Favorite
import com.example.domain.model.Movie
import com.example.domain.usecase.AddToFavoritesUseCase
import com.example.domain.usecase.ConsumeFavoritesUseCase
import com.example.domain.usecase.RemoveFromFavoritesUseCase
import com.example.domain.usecase.SearchMoviesUseCase
import com.example.presentation.mapper.MoviePresentationMapper
import com.example.presentation.model.MoviePresentation
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private lateinit var consumeFavoritesUseCase: ConsumeFavoritesUseCase
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase
    private lateinit var removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
    private lateinit var viewModel: MovieViewModel

    @Before
    fun setup() {
        searchMoviesUseCase = mockk()
        consumeFavoritesUseCase = mockk()
        addToFavoritesUseCase = mockk()
        removeFromFavoritesUseCase = mockk()

        viewModel = MovieViewModel(
            searchMoviesUseCase,
            consumeFavoritesUseCase,
            addToFavoritesUseCase,
            removeFromFavoritesUseCase
        )

        coEvery { searchMoviesUseCase.execute("Guardians") } returns listOf(
            Movie("Guardians of the Galaxy", "222", "id1","posterUrl1"),
            Movie("Guardians of the Galaxy Vol. 2", "333", "id2", "posterUrl2")
        )

        coEvery { consumeFavoritesUseCase.execute() } returns flowOf(
            listOf(Favorite("id1"))
        )
        coEvery { addToFavoritesUseCase.execute(any()) } just Runs
        coEvery { removeFromFavoritesUseCase.execute(any()) } just Runs
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `searchMovies should post movie list to movieListLiveData`() = runTest {
        val movieList = listOf(
            Movie("Guardians of the Galaxy", "222", "id1","posterUrl1"),
            Movie("Guardians of the Galaxy Vol. 2", "333", "id2", "posterUrl2")
        )
        val favoriteList = listOf(Favorite("id1"))

        coEvery { searchMoviesUseCase.execute("Guardians") } returns movieList
        coEvery { consumeFavoritesUseCase.execute() } returns flowOf(favoriteList)

        val observer = mockk<Observer<List<MoviePresentation>>>(relaxed = true)
        viewModel.movieListLiveData.observeForever(observer)

        viewModel.searchMovies("Guardians")
        advanceUntilIdle()

        val expectedMovies = MoviePresentationMapper.mapToPresentationList(movieList, favoriteList)
        verify { observer.onChanged(expectedMovies) }
        assertEquals(expectedMovies, viewModel.movieListLiveData.value)
        viewModel.movieListLiveData.removeObserver(observer)
    }

    @Test
    fun `toggleFavorite should remove from favorites when movie is already favorite`() = runTest {
        val movie = MoviePresentation("Guardians", "posterUrl", "id1", true)

        viewModel.toggleFavorite(movie)
        advanceUntilIdle()

        coVerify { removeFromFavoritesUseCase.execute(Favorite("id1")) }
    }

    @Test
    fun `refreshFavorites should post favorite movies to favoriteMoviesLiveData`() = runTest {
        val movieList = listOf(
            Movie("Guardians of the Galaxy", "222", "id1","posterUrl1"),
            Movie("Another Movie", "333", "id2", "posterUrl2")
        )
        val favoriteList = listOf(Favorite("id1"))

        coEvery { searchMoviesUseCase.execute("Guardians") } returns movieList
        coEvery { consumeFavoritesUseCase.execute() } returns flowOf(favoriteList)

        viewModel.refreshFavorites()
        advanceUntilIdle()

        val expectedFavorites = movieList.filter { it.imdbID == "id1" }.map {
            MoviePresentationMapper.mapToPresentation(it, true)
        }
        assertEquals(expectedFavorites, viewModel.favoriteMoviesLiveData.value)
    }

    @Test
    fun `onMovieClick should post imdbID to navigateToMovieDetails`() {
        val observer = mockk<Observer<String?>>(relaxed = true)
        viewModel.navigateToMovieDetails.observeForever(observer)

        val imdbID = "id1"
        viewModel.onMovieClick(imdbID)

        verify { observer.onChanged(imdbID) }
    }

    @Test
    fun `onMovieDetailsNavigated should reset navigateToMovieDetails`() {
        val observer = mockk<Observer<String?>>(relaxed = true)
        viewModel.navigateToMovieDetails.observeForever(observer)

        viewModel.onMovieClick("id1")
        viewModel.onMovieDetailsNavigated()

        verify { observer.onChanged(null) }
    }
}