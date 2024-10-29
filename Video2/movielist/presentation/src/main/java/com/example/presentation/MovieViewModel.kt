package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SearchMoviesUseCase
import com.example.presentation.mapper.MoviePresentationMapper
import com.example.presentation.model.MoviePresentation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.domain.model.Favorite
import com.example.domain.usecase.AddToFavoritesUseCase
import com.example.domain.usecase.ConsumeFavoritesUseCase
import com.example.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    val movieListLiveData = MutableLiveData<List<MoviePresentation>>()
    private val errorLiveData = MutableLiveData<String>()
    val favoriteMoviesLiveData = MutableLiveData<List<MoviePresentation>>()

    private val _navigateToMovieDetails = MutableLiveData<String?>()
    val navigateToMovieDetails: LiveData<String?> = _navigateToMovieDetails

    private var movies: List<MoviePresentation> = emptyList()
    init {
        refreshMovies()
        refreshFavorites()
    }

    private fun searchMovies(title: String) {
        viewModelScope.launch {
            try {
                val movieList = withContext(Dispatchers.IO) {
                    searchMoviesUseCase.execute(title)
                }

                val favorites = withContext(Dispatchers.IO) {
                    consumeFavoritesUseCase.execute().first()
                }

                movies = MoviePresentationMapper.mapToPresentationList(movieList, favorites)
                movieListLiveData.postValue(movies)
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies: ${e.message}")
                errorLiveData.postValue(e.message)
            }
        }
    }
    fun refreshMovies() {
        searchMovies("Guardians")
    }

    fun toggleFavorite(movie: MoviePresentation) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                removeFromFavoritesUseCase.execute(Favorite(movie.imdbID))
            } else {
                addToFavoritesUseCase.execute(Favorite(movie.imdbID))
            }
            val updatedMovies = movies.map {
                if (it.imdbID == movie.imdbID) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
            movies = updatedMovies
            movieListLiveData.postValue(updatedMovies)
            refreshFavorites()
        }
    }


    fun refreshFavorites() {
        viewModelScope.launch {
            if (movies.isEmpty()) {
                val loadedMovies = searchMoviesUseCase.execute("Guardians")
                val favorites = consumeFavoritesUseCase.execute().first()
                movies = MoviePresentationMapper.mapToPresentationList(loadedMovies, favorites)
            }
            val favorites = consumeFavoritesUseCase.execute().first()
            val favoritePresentationList = movies.filter { movie ->
                favorites.any { it.id == movie.imdbID }
            }.map { movie ->
                movie.copy(isFavorite = true)
            }
            favoriteMoviesLiveData.postValue(favoritePresentationList)
        }
    }

    fun onMovieClick(imdbID: String) {
        _navigateToMovieDetails.postValue(imdbID)
    }
    fun onMovieDetailsNavigated() {
        _navigateToMovieDetails.postValue(null)
    }
}