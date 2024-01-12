package com.homedepot.sa.xp.logprocessor.logs

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import com.homedepot.sa.xp.logprocessor.core.di.manual.AppModule
import com.homedepot.sa.xp.logprocessor.core.di.manual.AppModuleImpl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named


class LogProcessor(
    private val module: AppModule
) {

    companion object {
        @get:JvmSynthetic
        @Volatile
        private var instance: LogProcessor? = null

        fun initialize(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        HDPLog.plant(HDPLog.DebugTree())
                        val module = AppModuleImpl(context)
                        instance = LogProcessor(module)
                    }
                }
            }
        }

        fun getInstance(): LogProcessor {
            return instance ?: throw IllegalStateException(
                "LogProcessor is not initialized. Call initialize() first."
            )
        }

        fun d(message: String) {
            HDPLog.d(message)
        }

    }

    private suspend fun getMessageTest(): String {
        return getInstance().module.configurationManager.getTest()
    }

}
