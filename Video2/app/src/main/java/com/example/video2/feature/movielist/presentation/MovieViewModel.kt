package com.example.video2.feature.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.video2.feature.moviefavorites.domain.usecase.ConsumeFavoritesUseCase
import com.example.video2.feature.movielist.domain.usecase.SearchMoviesUseCase
import com.example.video2.feature.movielist.presentation.mapper.MoviePresentationMapper
import com.example.video2.feature.movielist.presentation.model.MoviePresentation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase
) : ViewModel() {

    val movieListLiveData = MutableLiveData<List<MoviePresentation>>()
    private val errorLiveData = MutableLiveData<String>()

    private val _navigateToMovieDetails = MutableLiveData<String?>()
    val navigateToMovieDetails: LiveData<String?> = _navigateToMovieDetails

    fun searchMovies(title: String) {
        viewModelScope.launch {
            try {
                val movieList = withContext(Dispatchers.IO) {
                    searchMoviesUseCase.execute(title)
                }

                val favorites = withContext(Dispatchers.IO) {
                    consumeFavoritesUseCase.execute().first()
                }

                val presentationList = MoviePresentationMapper.mapToPresentationList(movieList, favorites)
                movieListLiveData.postValue(presentationList)
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies: ${e.message}")
                errorLiveData.postValue(e.message)
            }
        }
    }

    fun onMovieClick(imdbID: String) {
        _navigateToMovieDetails.postValue(imdbID)
    }

    fun onMovieDetailsNavigated() {
        _navigateToMovieDetails.postValue(null)
    }
}