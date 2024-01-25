package com.homedepot.sa.xp.logprocessor.core.domain.repositories

import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SaveLogUseCase

interface LogRepository {
    suspend fun saveLog(params: SaveLogUseCase.Params)
    suspend fun sendLogs()
}