package ru.gb.android.homework3

import android.app.Application

class MarketSampleApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MarketSampleApp? = null
        fun getInstance(): MarketSampleApp = instance!!
    }
}
