package com.homedepot.sa.xp.logprocessor.logs.data.mappers

import com.homedepot.sa.xp.logprocessor.logs.data.models.Level

fun Level.asInt() = when (this) {
    Level.DebugInt -> 0
    Level.ErrorInt -> 1
    Level.FatalInt -> 2
    Level.InfoInt -> 3
    Level.TraceInt -> 4
    Level.WarnInt -> 5
}