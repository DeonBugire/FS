package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var startWorkButton: Button
    private lateinit var cancelWorkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWorkButton = findViewById(R.id.startWorkButton)
        cancelWorkButton = findViewById(R.id.cancelWorkButton)

        startWorkButton.setOnClickListener {
            val workRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES).build()
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                WORK_ID,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }

        cancelWorkButton.setOnClickListener {
            WorkManager.getInstance(this).cancelUniqueWork(WORK_ID)
        }
    }

    companion object {
        private const val WORK_ID = "MyUniqueWork"
    }
}