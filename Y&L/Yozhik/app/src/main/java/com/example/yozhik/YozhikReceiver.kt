package com.example.yozhik

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler

class YozhikReceiver : BroadcastReceiver() {
    var callsCounter = 0

    override fun onReceive(context: Context, intent: Intent) {

        val result = goAsync()
        val thread: Thread = object : Thread() {
            override fun run() {

                if (intent.action == "Yozhik!") {
                    callsCounter++
                    val handler = Handler(context.mainLooper)
                    handler.post {
                        MainActivity.updateCounters()
                    }
                    sleep(3000)
                    context.sendBroadcast(Intent("Loshadka!", ), "com.example.loshadka.CALL_IN_THE_MIST")
                }
                result.resultCode = callsCounter
                result.finish()
            }
        }
        thread.start()
    }
}