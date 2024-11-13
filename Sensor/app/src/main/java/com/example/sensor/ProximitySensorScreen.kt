package com.example.sensor

import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ProximitySensorScreen(
    isScreenOn: Boolean,
    isCallModeEnabled: Boolean,
    onCallModeToggle: () -> Unit
) {
    val screenStatusText = if (isScreenOn) "Экран включен" else "Экран выключен (рядом с ухом)"
    val callModeText = if (isCallModeEnabled) "Отключить режим звонка" else "Включить режим звонка"

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
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                Log.d("ProximitySensorScreen", "Button clicked")
                onCallModeToggle()
            }) {
                Text(text = callModeText)
            }
        }
    }
}