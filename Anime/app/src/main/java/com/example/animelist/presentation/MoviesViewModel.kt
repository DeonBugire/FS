package com.example.animelist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.usecase.*
import com.example.animelist.presentation.mapper.MoviePresentationMapper
import com.example.animelist.presentation.model.MoviePresentation
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.await
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    val tabIndex = MutableLiveData(0)
    val movieListLiveData = MutableLiveData<List<MoviePresentation>>()
    val favoriteListLiveData = MutableLiveData<List<MoviePresentation>>()
    val navigateToMovieDetails = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun setTab(index: Int) {
        tabIndex.value = index
    }

    fun searchMovies(title: String) {
        viewModelScope.launch {
            val movieList = searchMoviesUseCase.execute(title).await()
            getFavoritesUseCase.execute().collect { favoriteMovies ->
                val presentationList = MoviePresentationMapper.mapToPresentationList(movieList, favoriteMovies)
                movieListLiveData.postValue(presentationList)
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase.execute().collect { favoriteMovies ->
                val disposable = searchMoviesUseCase.execute("")
                    .subscribe({ movieList ->
                        val presentationList = MoviePresentationMapper.mapToPresentationList(movieList, favoriteMovies)
                        favoriteListLiveData.postValue(presentationList)
                    }, { error ->
                    })

                compositeDisposable.add(disposable)
            }
        }
    }

    fun toggleFavorite(imdbID: String) {
        viewModelScope.launch {
            isFavoriteUseCase.execute(imdbID).collect { isFavorite ->
                if (isFavorite) {
                    removeFromFavoritesUseCase.execute(imdbID)
                } else {
                    addToFavoritesUseCase.execute(imdbID)
                }
                loadFavorites()
            }
        }
    }

    // Реализация перехода к деталям фильма
    fun onMovieClick(imdbID: String) {
        navigateToMovieDetails.postValue(imdbID)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}