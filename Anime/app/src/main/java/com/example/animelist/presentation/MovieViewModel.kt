package com.example.animelist.presentation

import androidx.lifecycle.ViewModel
import com.example.animelist.domain.usecase.GetFavoritesUseCase
import com.example.animelist.domain.usecase.SearchMoviesUseCase
import com.example.animelist.presentation.mapper.MoviePresentationMapper
import com.example.animelist.presentation.model.MoviePresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MovieViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieListLiveData = MutableLiveData<List<MoviePresentation>>()
    val errorLiveData = MutableLiveData<String>()

    fun searchMovies(title: String) {
        val disposable = searchMoviesUseCase.execute(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieList ->
                Log.d("MovieViewModel", "Movies received: ${movieList.size}")
                val favoriteEntities = runBlocking {
                    getFavoritesUseCase.execute().first()
                }
                val presentationList = MoviePresentationMapper.mapToPresentationList(movieList, favoriteEntities)
                movieListLiveData.value = presentationList
            }, { error ->
                Log.e("MovieViewModel", "Error fetching movies: ${error.message}")
                errorLiveData.value = error.message
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}


