package com.homedepot.sa.xp.logprocessor.core.data.repositories

import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.FileDownloaded
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.ConfigurationRepository
import okhttp3.ResponseBody
import javax.inject.Inject

internal class ConfigurationRepositoryImpl @Inject constructor(
    private val localDataSource: ConfigurationLocalDataSource,
    private val remoteDataSource: ConfigurationRemoteDataSource
) : ConfigurationRepository {
    override suspend fun getPlatformConfiguration(): PlatformConfigurationDto? {
        return localDataSource.getPlatformConfiguration()
    }

    override suspend fun getLogConfiguration(): LogConfigurationDto? {
        return localDataSource.getLogConfiguration()
    }

    override suspend fun getLogConfigurationDefault(): LogConfigurationDto? {
        return localDataSource.getLogConfigurationDefault()
    }

    override suspend fun saveLogConfigurationFile(fileDownloaded: FileDownloaded) {
        localDataSource.saveLogConfigurationFile(fileDownloaded)
    }

    override suspend fun downloadConfiguration(): ResponseBody {
        return remoteDataSource.downloadConfiguration()
    }

    override suspend fun getTest(): String {
        return localDataSource.getTest()
    }

}

internal interface ConfigurationRemoteDataSource {
    suspend fun downloadConfiguration(): ResponseBody
}

internal interface ConfigurationLocalDataSource {
    suspend fun getTest(): String
    suspend fun getPlatformConfiguration(): PlatformConfigurationDto?
    suspend fun getLogConfiguration(): LogConfigurationDto?
    suspend fun getLogConfigurationDefault(): LogConfigurationDto?
    suspend fun saveLogConfigurationFile(fileDownloaded: FileDownloaded)
}