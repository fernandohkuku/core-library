package com.homedepot.sa.xp.logprocessor.common

import android.app.Application
import com.homedepot.sa.xp.logprocessor.logs.presentation.log.LogProcessor
import dagger.hilt.android.HiltAndroidApp

class LogProcessorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LogProcessor.initialize(this, isDebug = false)
    }
}