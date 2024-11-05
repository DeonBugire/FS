package com.example.yozhik

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

val receiver = YozhikReceiver()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.MyTextView)

        val filter = IntentFilter("Yozhik!")

        registerReceiver(receiver, filter, RECEIVER_EXPORTED)

        sendBroadcast(Intent("Loshadka!"));
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onResume() {
        super.onResume()
        updateCounters()
    }

    companion object {
        private lateinit var textView: TextView
        fun updateCounters() {
            if (::textView.isInitialized) {
                textView.text = "Yezhik heard loshadka " + receiver.callsCounter + " times"
            }
        }
    }
}