package com.example.timers.ui

import androidx.compose.runtime.Composable

@Composable
fun formatTime(time: String, showSeconds: Boolean): String {
    return if (showSeconds) {
        time
    } else {
        time.substring(0, 2)
    }
}