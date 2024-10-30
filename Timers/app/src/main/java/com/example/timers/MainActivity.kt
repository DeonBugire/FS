package com.example.timers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.timers.di.DaggerAppComponent
import com.example.timers.presentation.factory.TimerViewModelFactory
import com.example.timers.ui.TimerScreen
import com.example.timers.presentation.vm.TimerViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: TimerViewModelFactory
    private lateinit var viewModel1: TimerViewModel
    private lateinit var viewModel2: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)

        viewModel1 = ViewModelProvider(this, viewModelFactory).get(
            TimerViewModel.TIMER1_KEY,
            TimerViewModel::class.java
        )
        viewModel2 = ViewModelProvider(this, viewModelFactory).get(
            TimerViewModel.TIMER2_KEY,
            TimerViewModel::class.java
        )

        setContent {
            TimerScreen(viewModel1 = viewModel1, viewModel2 = viewModel2)
        }
    }
}