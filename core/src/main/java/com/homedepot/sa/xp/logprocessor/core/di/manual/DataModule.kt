package com.homedepot.sa.xp.logprocessor.core.di.manual

import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationLocalDataSource
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRemoteDataSource
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRepositoryImpl
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.ConfigurationRepository

internal class DataModule(
    private val configurationLocalDataSource: ConfigurationLocalDataSource,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource
) {

    @get:JvmSynthetic
    internal val configurationManager: ConfigurationRepository by lazy {
        ConfigurationRepositoryImpl(
            localDataSource = configurationLocalDataSource,
            remoteDataSource = configurationRemoteDataSource
        )
    }
}