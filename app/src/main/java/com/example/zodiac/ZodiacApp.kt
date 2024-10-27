package com.example.zodiac

import com.example.core.CoreApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZodiacApp : CoreApplication() {
    companion object {

        private lateinit var app: ZodiacApp

        fun instance(): ZodiacApp {
            return app
        }

    }

    var isHasNetwork: Boolean = false

    override fun onCreate() {
        super.onCreate()
        app = this

    }
}