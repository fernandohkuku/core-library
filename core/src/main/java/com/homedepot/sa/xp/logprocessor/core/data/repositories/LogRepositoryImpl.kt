package com.homedepot.sa.xp.logprocessor.core.data.repositories

import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.log.LogDto
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.LogRepository
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SaveLogUseCase
import javax.inject.Inject

internal class LogRepositoryImpl @Inject constructor(
    private val localDataSource: LogLocalDataSource
) : LogRepository {
    override suspend fun saveLog(params: SaveLogUseCase.Params) {
        val (level, message, tag, throwable) = params
        localDataSource.saveLog(level, message, tag, throwable?.message)
    }

    override suspend fun sendLogs() {
    }
}


internal interface LogLocalDataSource {
    suspend fun saveLog(level: Int, message: String, tag: String, error: String?)
    suspend fun getLogs(): List<LogDto>
}