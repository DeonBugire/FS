package com.example.video2.feature.moviedetail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.video2.feature.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.example.video2.feature.moviedetail.presentation.mapper.MovieDetailsPresentationMapper
import com.example.video2.feature.moviedetail.presentation.model.MovieDetailsPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetailsPresentation?>(null)
    val movieDetails: StateFlow<MovieDetailsPresentation?> = _movieDetails

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            getMovieDetailsUseCase.execute(imdbID).collect { movieDetails ->
                _movieDetails.value = MovieDetailsPresentationMapper.mapToPresentation(movieDetails)
            }
        }
    }
}