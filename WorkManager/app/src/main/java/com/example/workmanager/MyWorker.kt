package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val channelId = "CHANNEL_ID"
    private val notificationId = 1
    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    override suspend fun doWork(): Result {
        for (i in 30 downTo 1) {
            val progress = "Work In Progress Countdown: $i"
            setForeground(createForegroundInfo(progress))
            delay(1000)
        }
        return Result.success()
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("My Work")
            .setContentText(progress)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .addAction(android.R.drawable.ic_delete, "Cancel", intent)
            .build()

        return ForegroundInfo(notificationId, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }
}