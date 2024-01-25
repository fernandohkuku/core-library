package com.homedepot.sa.xp.logprocessor.logs.presentation.log

import android.util.Log
import com.homedepot.sa.xp.logprocessor.core.di.manual.AppModule
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SaveLogUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber


internal class LogsProduction(
    private val saveLogUseCase: SaveLogUseCase
) : Timber.Tree() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        scope.launch {
            when (priority) {
                Log.ERROR, Log.INFO, Log.WARN, Log.DEBUG -> {
                    val params = SaveLogUseCase.Params(
                        level = priority,
                        message = message,
                        tag = tag.toString(),
                        throwable = t
                    )
                    saveLogUseCase(params).fold(onFailure = {

                    })
                }
            }
        }
    }
}