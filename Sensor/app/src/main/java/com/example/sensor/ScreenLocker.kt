package com.example.sensor

import android.content.Context
import android.os.PowerManager
import android.util.Log

class ScreenLocker(private val context: Context) {
    private val powerManager: PowerManager =
        context.getSystemService(Context.POWER_SERVICE) as PowerManager
    private var wakeLock: PowerManager.WakeLock? = null

    fun lockScreen() {
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock(
                PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
                "ProximitySensor:WakeLock"
            )
            Log.d("ScreenLocker", "WakeLock создан")
        }
        if (wakeLock?.isHeld == false) {
            wakeLock?.acquire()
            Log.d("ScreenLocker", "WakeLock активирован")
        }
    }

    fun unlockScreen() {
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
            Log.d("ScreenLocker", "WakeLock освобожден")
        }
    }

    fun release() {
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
            Log.d("ScreenLocker", "WakeLock освобожден через release()")
        }
    }
}