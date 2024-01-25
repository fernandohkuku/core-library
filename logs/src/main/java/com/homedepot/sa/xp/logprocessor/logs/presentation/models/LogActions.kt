package com.homedepot.sa.xp.logprocessor.logs.presentation.models

import com.homedepot.sa.xp.logprocessor.logs.data.models.Level

internal interface LogActions {
    fun d(level: Level, message: String, tag: String)
    fun i(level: Level, message: String, tag: String)
    fun e(level: Level, message: String, throwable: Throwable)
    fun w(level: Level, message: String, tag: String)
    fun wtf(level: Level, message: String, tag: String)
}