package com.example.loshadka

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

val receiver = LoshadkaReceiver()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.MyTextView)

        val filter = IntentFilter("Loshadka!")

        registerReceiver(receiver, filter, RECEIVER_EXPORTED)
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
                textView.text = "Loshadka heard Yezhik " + receiver.callsCounter + " times"
            }
        }
    }
}