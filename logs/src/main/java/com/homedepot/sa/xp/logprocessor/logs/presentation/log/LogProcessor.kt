package com.homedepot.sa.xp.logprocessor.logs.presentation.log

import android.content.Context
import androidx.annotation.IdRes
import com.homedepot.sa.xp.logprocessor.core.di.manual.AppModule
import com.homedepot.sa.xp.logprocessor.core.di.manual.AppModuleImpl
import com.homedepot.sa.xp.logprocessor.core.infrastructure.worker.LogWorkManager
import com.homedepot.sa.xp.logprocessor.logs.data.mappers.asInt
import com.homedepot.sa.xp.logprocessor.logs.data.models.Level
import timber.log.Timber


class LogProcessor private constructor(
    private val module: AppModule,
    private val context: Context
) {
    companion object {
        @get:JvmSynthetic
        @Volatile
        private var mInstance: LogProcessor? = null
        fun initialize(context: Context, isDebug: Boolean = true) {
            if (mInstance == null) {
                synchronized(this) {
                    if (mInstance == null) {
                        AppModuleImpl(context).apply {
                            initializeLogProcessor(this, context)
                            initializeTimber(isDebug, this)
                            LogWorkManager.enqueuePeriodicWorker(context, sendLogsUseCase)
                        }
                    }
                }
            }
        }

        private fun initializeLogProcessor(
            module: AppModule,
            context: Context
        ) {
            mInstance = LogProcessor(module, context)
        }

        private fun initializeTimber(
            isDebug: Boolean,
            module: AppModule
        ) {
            val logsProduction = LogsProduction(module.saveLogUseCase)

            if (isDebug) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(logsProduction)
            }
        }


        private val instance: LogProcessor
            get() = mInstance
                ?: throw IllegalStateException("LogProcessor is not initialized. Call initialize() first.")

        fun d(message: String?, tag: String) {
            Timber.tag(tag).d(message)
        }

        fun d(level: Level, message: String?, tagResource: Int) {
            val levelInt = level.asInt()
            val tag = instance.context.getString(tagResource)
            Timber.tag(tag).d(message, levelInt)
        }

        fun d(tag: String, level: Level, message: String?, vararg args: Any?) {
            val levelInt = level.asInt()
            Timber.d(message, levelInt)
        }

        fun d(message: String?, tag: String, vararg args: Any?) {
            Timber.d(message)
        }

        fun d(t: Throwable) {
            Timber.d(t)
        }
    }
}


