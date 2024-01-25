package com.homedepot.sa.xp.logprocessor.core.domain.usecases.log

import com.homedepot.sa.xp.logprocessor.core.domain.repositories.LogRepository
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SendLogsUseCase @Inject constructor(
    private val repository: LogRepository,
    background: CoroutineDispatcher
) : UseCase<Unit, Unit>(background) {
    override suspend fun run(input: Unit?) {
        repository.sendLogs()
    }
}