package com.example.animelist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.domain.usecase.GetMovieDetailsUseCase
import com.example.animelist.domain.usecase.IsFavoriteUseCase
import com.example.animelist.domain.usecase.AddToFavoritesUseCase
import com.example.animelist.domain.usecase.RemoveFromFavoritesUseCase
import com.example.animelist.presentation.model.MovieDetailsPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetailsLiveData = MutableLiveData<MovieDetailsPresentation>()
    val isFavoriteLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun getMovieDetails(imdbID: String) {
        val disposable = getMovieDetailsUseCase.execute(imdbID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieDetails ->
                movieDetailsLiveData.value = movieDetails
                checkIfFavorite(imdbID)
            }, { error ->
                errorLiveData.value = error.message
            })

        compositeDisposable.add(disposable)
    }

    fun checkIfFavorite(imdbID: String) {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCase.execute(imdbID).first()
            isFavoriteLiveData.postValue(isFavorite)
        }
    }

    fun toggleFavorite(imdbID: String) {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCase.execute(imdbID).first()
            if (isFavorite) {
                removeFromFavoritesUseCase.execute(imdbID)
            } else {
                addToFavoritesUseCase.execute(imdbID)
            }
            checkIfFavorite(imdbID)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}