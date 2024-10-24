package com.example.video2.feature.moviedetail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.video2.feature.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.example.video2.feature.moviedetail.presentation.mapper.MovieDetailsPresentationMapper
import com.example.video2.feature.moviedetail.presentation.model.MovieDetailsPresentation
import com.example.video2.feature.moviefavorites.domain.usecase.ConsumeFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetailsPresentation?>(null)
    val movieDetails: StateFlow<MovieDetailsPresentation?> = _movieDetails

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            val favorites = consumeFavoritesUseCase.execute().firstOrNull() ?: emptyList()
            getMovieDetailsUseCase.execute(imdbID).collect { movieDetails ->
                val isFavorite = favorites.any { it.id == imdbID }
                _movieDetails.value = MovieDetailsPresentationMapper.mapToPresentation(movieDetails, isFavorite)
            }
        }
    }
}