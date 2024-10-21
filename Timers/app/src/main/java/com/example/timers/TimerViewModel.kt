package com.example.timers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
    private val timerUseCase: TimerUseCase
) : ViewModel() {
    private val _timerLiveData = MutableLiveData<String>()
    val timerLiveData: LiveData<String> = _timerLiveData

    private var timerDisposable: Disposable? = null

    fun startTimer(timeInSeconds: Long) {
        cancelTimer()

        timerDisposable = timerUseCase.startTimer(timeInSeconds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { formattedTime ->
                    _timerLiveData.postValue(formattedTime)
                },
                { error ->
                    Log.e("TimerViewModel", "Error: ${error.localizedMessage}")
                }
            )
    }

    fun cancelTimer() {
        timerDisposable?.dispose()
        timerDisposable = null
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
}
