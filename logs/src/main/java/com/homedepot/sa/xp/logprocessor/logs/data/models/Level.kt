package com.homedepot.sa.xp.logprocessor.logs.data.models

sealed interface Level  {
    data object DebugInt : Level
    data object TraceInt : Level
    data object InfoInt : Level
    data object ErrorInt : Level
    data object FatalInt : Level
    data object WarnInt : Level
}

val Level.name: String
    get() = this::class.simpleName.toString()