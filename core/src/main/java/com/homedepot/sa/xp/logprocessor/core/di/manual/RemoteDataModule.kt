package com.homedepot.sa.xp.logprocessor.core.di.manual

import com.homedepot.sa.xp.logprocessor.core.data.remote.api.configurator.ConfiguratorService
import com.homedepot.sa.xp.logprocessor.core.data.remote.source.ConfigurationRemoteDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRemoteDataSource
import retrofit2.Retrofit

internal class RemoteDataModule(
    private val retrofit: Retrofit
) {
    private val configurationService: ConfiguratorService
        get() = retrofit.create(ConfiguratorService::class.java)

    val configurationRemoteDataSource: ConfigurationRemoteDataSource
        get() = ConfigurationRemoteDataSourceImpl(configurationService)


}