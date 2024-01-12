package com.homedepot.sa.xp.logprocessor.core.data.remote.source

import com.homedepot.sa.xp.logprocessor.core.data.remote.api.configurator.ConfiguratorService
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRemoteDataSource
import okhttp3.ResponseBody
import javax.inject.Inject

internal class ConfigurationRemoteDataSourceImpl @Inject constructor(
    private val configurationService: ConfiguratorService
) : ConfigurationRemoteDataSource {
    override suspend fun downloadConfiguration(): ResponseBody {
        return configurationService.downloadConfigurator("")
    }
}