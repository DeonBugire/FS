package com.example.animelist.presentation

import androidx.lifecycle.ViewModel
import com.example.animelist.domain.usecase.GetMovieDetailsUseCase
import com.example.animelist.presentation.model.MovieDetailsPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetailsLiveData = MutableLiveData<MovieDetailsPresentation>()
    val errorLiveData = MutableLiveData<String>()

    fun getMovieDetails(imdbID: String) {
        val disposable = getMovieDetailsUseCase.execute(imdbID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieDetails ->
                movieDetailsLiveData.value = movieDetails
            }, { error ->
                errorLiveData.value = error.message
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}