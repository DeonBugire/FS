package com.example.timers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.timers.ui.theme.TimersTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: TimerViewModelFactory
    private lateinit var viewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimerViewModel::class.java)

        setContent {
            TimerScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val timeState = viewModel.timerLiveData.observeAsState("00:00")
    val time = timeState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time, // Выводим время
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { viewModel.startTimer(5 * 60) }, // Таймер на 5 минут
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Start Timer")
            }

            Button(
                onClick = { viewModel.cancelTimer() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Cancel Timer")
            }
        }
    }
}
