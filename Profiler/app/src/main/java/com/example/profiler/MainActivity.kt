package com.example.profiler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    private lateinit var startWorkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWorkButton = findViewById(R.id.startWorkButton)

        startWorkButton.setOnClickListener {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val myWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<MyWorker>()
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(this)
                .enqueueUniqueWork("unique work",
                    ExistingWorkPolicy.KEEP,
                    myWorkRequest)
        }
    }
}