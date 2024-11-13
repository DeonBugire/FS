package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*


class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private var isScreenOn by mutableStateOf(true)
    private var isCallModeEnabled by mutableStateOf(false)
    private lateinit var screenLocker: ScreenLocker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        screenLocker = ScreenLocker(this)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        setContent {
            ProximitySensorScreen(
                isScreenOn = isScreenOn,
                isCallModeEnabled = isCallModeEnabled,
                onCallModeToggle = { toggleCallMode() }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (isCallModeEnabled) {
            proximitySensor?.also { sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                Log.d("MainActivity", "Слушатель датчика зарегистрирован")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        screenLocker.release()
        Log.d("MainActivity", "Слушатель датчика и WakeLock освобождены")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (isCallModeEnabled && event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            isScreenOn = event.values[0] >= (proximitySensor?.maximumRange ?: 0f)
            if (isScreenOn) {
                Log.d("MainActivity", "Состояние: экран включен")
                screenLocker.unlockScreen()
            } else {
                Log.d("MainActivity", "Состояние: экран выключен")
                screenLocker.lockScreen()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        screenLocker.release()
    }

    private fun toggleCallMode() {
        isCallModeEnabled = !isCallModeEnabled
        Log.d("MainActivity", "Call mode toggled: $isCallModeEnabled")
        if (isCallModeEnabled) {
            proximitySensor?.also { sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                Log.d("MainActivity", "Слушатель сенсора зарегистрирован")
            }
        } else {
            sensorManager.unregisterListener(this)
            screenLocker.unlockScreen()
            Log.d("MainActivity", "Слушатель сенсора отключен и WakeLock освобожден")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}