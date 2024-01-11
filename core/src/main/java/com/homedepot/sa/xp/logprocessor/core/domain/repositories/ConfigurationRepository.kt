package com.homedepot.sa.xp.logprocessor.core.domain.repositories

import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.FileDownloaded
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import okhttp3.ResponseBody


interface ConfigurationRepository {
    suspend fun getPlatformConfiguration(): PlatformConfigurationDto?
    suspend fun getLogConfiguration(): LogConfigurationDto?
    suspend fun getLogConfigurationDefault(): LogConfigurationDto?
    suspend fun saveLogConfigurationFile(fileDownloaded: FileDownloaded)
    suspend fun downloadConfiguration(): ResponseBody
    suspend fun getTest(): String
}