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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    private lateinit var viewModel1: TimerViewModel
    private lateinit var viewModel2: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
        viewModel1 = ViewModelProvider(this, viewModelFactory).get("Timer1", TimerViewModel::class.java)
        viewModel2 = ViewModelProvider(this, viewModelFactory).get("Timer2", TimerViewModel::class.java)

        setContent {
            TimerScreen(viewModel1 = viewModel1, viewModel2 = viewModel2)
        }
    }
}

@Composable
fun TimerScreen(viewModel1: TimerViewModel, viewModel2: TimerViewModel) {
    val showSeconds = remember { mutableStateOf(true) }

    val timeState1 = viewModel1.timerLiveData.observeAsState("00:00")
    val timeState2 = viewModel2.timerLiveData.observeAsState("00:00")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Show seconds")
            Switch(
                checked = showSeconds.value,
                onCheckedChange = { showSeconds.value = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Первый таймер
        Text(
            text = "Timer 1: ${formatTime(timeState1.value, showSeconds.value)}",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Button(
                onClick = { viewModel1.startTimer(5 * 60) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Start Timer 1")
            }

            Button(
                onClick = { viewModel1.cancelTimer() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Cancel Timer 1")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Второй таймер
        Text(
            text = "Timer 2: ${formatTime(timeState2.value, showSeconds.value)}",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Button(
                onClick = { viewModel2.startTimer(3 * 60) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Start Timer 2")
            }

            Button(
                onClick = { viewModel2.cancelTimer() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Cancel Timer 2")
            }
        }
    }
}

@Composable
fun formatTime(time: String, showSeconds: Boolean): String {
    return if (showSeconds) {
        time
    } else {
        time.substring(0, 2)
    }
}
