package com.homedepot.sa.xp.logprocessor.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogProcessorApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}