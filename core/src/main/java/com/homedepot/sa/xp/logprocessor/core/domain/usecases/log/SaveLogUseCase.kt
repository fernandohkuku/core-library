package com.homedepot.sa.xp.logprocessor.core.domain.usecases.log

import com.homedepot.sa.xp.logprocessor.core.domain.repositories.LogRepository
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveLogUseCase @Inject constructor(
    private val repository: LogRepository,
    background: CoroutineDispatcher
) : UseCase<Unit, SaveLogUseCase.Params>(background) {
    override suspend fun run(input: Params?) {
        requireNotNull(input) { "Log can't be null" }
        repository.saveLog(input)
    }

    data class Params(
        val level: Int,
        val message: String,
        val tag: String,
        val throwable: Throwable?
    )

}