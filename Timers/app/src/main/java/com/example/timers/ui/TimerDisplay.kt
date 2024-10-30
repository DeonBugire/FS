package com.example.timers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerDisplay(
    title: String,
    time: String,
    showSeconds: Boolean,
    onStart: () -> Unit,
    onCancel: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$title: ${formatTime(time, showSeconds)}",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Button(
                onClick = onStart,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Start $title")
            }

            Button(
                onClick = onCancel,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Cancel $title")
            }
        }
    }
}