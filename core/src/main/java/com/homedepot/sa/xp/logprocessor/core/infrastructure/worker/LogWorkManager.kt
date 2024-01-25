package com.homedepot.sa.xp.logprocessor.core.infrastructure.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SendLogsUseCase
import java.util.concurrent.TimeUnit

class LogWorkManager(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val sendLogsUseCase: SendLogsUseCase
        get() = mSendLogsUseCase

    override suspend fun doWork(): Result {
        sendLogsUseCase()
        return Result.success()
    }

    companion object {
        private const val WORKER_TAG = "LogProcessorWorker"
        lateinit var mSendLogsUseCase: SendLogsUseCase
        fun enqueuePeriodicWorker(context: Context, sendLogsUseCase: SendLogsUseCase) {
            mSendLogsUseCase = sendLogsUseCase
            val workRequest = buildPeriodicWorkRequest()
            WorkManager.getInstance(context).enqueue(workRequest)
        }

        private fun buildPeriodicWorkRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<LogWorkManager>(
                15, TimeUnit.MINUTES,
            ).addTag(WORKER_TAG).build()
        }
    }
}