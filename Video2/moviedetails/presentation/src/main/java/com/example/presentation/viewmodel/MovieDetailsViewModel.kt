package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Favorite
import com.example.domain.usecase.AddToFavoritesUseCase
import com.example.domain.usecase.ConsumeFavoritesUseCase
import com.example.domain.usecase.GetMovieDetailsUseCase
import com.example.domain.usecase.RemoveFromFavoritesUseCase
import com.example.presentation.mapper.MovieDetailsPresentationMapper
import com.example.presentation.model.MovieDetailsPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetailsPresentation?>(null)
    val movieDetails: StateFlow<MovieDetailsPresentation?> = _movieDetails

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            val favorites = consumeFavoritesUseCase.execute().firstOrNull() ?: emptyList()
            val isFavorite = favorites.any { it.id == imdbID }
            getMovieDetailsUseCase.execute(imdbID).collect { movieDetails ->
                _movieDetails.value = MovieDetailsPresentationMapper.mapToPresentation(movieDetails, isFavorite)
            }
        }
    }

    fun toggleFavorite(movieDetails: MovieDetailsPresentation) {
        viewModelScope.launch {
            if (movieDetails.isFavorite) {
                removeFromFavoritesUseCase.execute(Favorite(movieDetails.imdbID))
            } else {
                addToFavoritesUseCase.execute(Favorite(movieDetails.imdbID))
            }
            _movieDetails.value = movieDetails.copy(isFavorite = !movieDetails.isFavorite)
        }
    }
}