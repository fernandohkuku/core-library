package com.homedepot.sa.xp.logprocessor.core.di.manual

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherModule {
    @get:JvmSynthetic
    val dispatcherIO: CoroutineDispatcher by lazy {
        Dispatchers.IO
    }
}