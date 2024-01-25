package com.homedepot.sa.xp.logprocessor.core.di.manual

import com.homedepot.sa.xp.logprocessor.core.domain.repositories.ConfigurationRepository
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.LogRepository
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SaveLogUseCase
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SendLogsUseCase
import kotlinx.coroutines.CoroutineDispatcher

internal class DomainModule(
    private val background: CoroutineDispatcher,
    private val logRepository: LogRepository
) {

    internal val saveLogUseCase by lazy {
        SaveLogUseCase(logRepository, background)
    }

    internal val sendLogsUseCase by lazy {
        SendLogsUseCase(logRepository, background)
    }

}