package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private var isScreenOn by mutableStateOf(true)
    private lateinit var powerManager: PowerManager
    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        setContent {
            ProximitySensorScreen(isScreenOn)
        }
    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        wakeLock?.release()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            isScreenOn = event.values[0] >= (proximitySensor?.maximumRange ?: 0f)
            controlScreen(isScreenOn)
        }
    }

    private fun controlScreen(turnOn: Boolean) {
        if (turnOn) {
            // Включаем экран и освобождаем wake lock, если он удерживается
            if (wakeLock?.isHeld == true) {
                wakeLock?.release()
            }
        } else {
            // Выключаем экран и удерживаем wake lock только если он еще не удерживается
            if (wakeLock == null) {
                wakeLock = powerManager.newWakeLock(
                    PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
                    "ProximitySensor:WakeLock"
                )
            }
            if (wakeLock?.isHeld == false) {
                wakeLock?.acquire()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}

@Composable
fun ProximitySensorScreen(isScreenOn: Boolean) {
    val screenStatusText = if (isScreenOn) "Экран включен" else "Экран выключен (рядом с ухом)"
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = screenStatusText, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

