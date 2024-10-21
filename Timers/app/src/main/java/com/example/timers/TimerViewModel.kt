package com.example.timers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TimerViewModel @Inject constructor(
    private val timerUseCase: TimerUseCase
) : ViewModel() {
    private val _timerLiveData = MutableLiveData<String>()
    val timerLiveData: LiveData<String> = _timerLiveData

    private val disposables = CompositeDisposable()

    fun startTimer(timeInSeconds: Long) {
        disposables.add(
            timerUseCase.startTimer(timeInSeconds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { formattedTime ->
                    _timerLiveData.postValue(formattedTime)
                }
        )
    }

    fun cancelTimer() {
        timerUseCase.cancelTimer()
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
