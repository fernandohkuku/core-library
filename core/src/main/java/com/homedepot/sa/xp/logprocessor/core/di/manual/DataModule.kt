package com.homedepot.sa.xp.logprocessor.core.di.manual

import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationLocalDataSource
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRemoteDataSource
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRepositoryImpl
import com.homedepot.sa.xp.logprocessor.core.data.repositories.LogLocalDataSource
import com.homedepot.sa.xp.logprocessor.core.data.repositories.LogRepositoryImpl
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.ConfigurationRepository
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.LogRepository

internal class DataModule(
    private val configurationLocalDataSource: ConfigurationLocalDataSource,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource,
    private val logLocalDataSource: LogLocalDataSource
) {

    @get:JvmSynthetic
    internal val configurationRepository: ConfigurationRepository by lazy {
        ConfigurationRepositoryImpl(
            localDataSource = configurationLocalDataSource,
            remoteDataSource = configurationRemoteDataSource
        )
    }


    @get:JvmSynthetic
    internal val logRepository : LogRepository by lazy {
        LogRepositoryImpl(
            localDataSource = logLocalDataSource
        )
    }
}