package com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration

import android.content.res.Resources
import com.homedepot.sa.xp.logprocessor.core.R
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.FileDownloaded
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationLocalDataSource
import com.homedepot.sa.xp.logprocessor.core.di.hilt.LOG_PROCESSOR_CONFIG
import com.homedepot.sa.xp.logprocessor.core.di.hilt.PLATFORM_CONFIG
import com.homedepot.sa.xp.logprocessor.core.domain.exceptions.configuration.PlatformConfigurationNotFoundException
import com.homedepot.sa.xp.logprocessor.ui_ktx.inputstream.saveFile
import com.squareup.moshi.JsonAdapter
import okio.use
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import javax.inject.Inject
import javax.inject.Named


internal class ConfigurationLocalDataSourceImpl @Inject constructor(
    private val resources: Resources,
    @Named(PLATFORM_CONFIG) private val platformConfigFile: File,
    @Named(LOG_PROCESSOR_CONFIG) private val logProcessorFile: File,
    private val logProcessorConfigurationAdapter: JsonAdapter<LogConfigurationDto>,
    private val platformConfigurationAdapter: JsonAdapter<PlatformConfigurationDto>,
) : ConfigurationLocalDataSource {
    override suspend fun getTest(): String {
        return "test"
    }

    override suspend fun getPlatformConfiguration(): PlatformConfigurationDto? {
        if (!platformConfigFile.exists()) {
            throw PlatformConfigurationNotFoundException()
        }

        val json = FileReader(platformConfigFile).use(FileReader::readText)
        return platformConfigurationAdapter.fromJson(json)
    }

    override suspend fun getLogConfiguration(): LogConfigurationDto? {
        if (!logProcessorFile.exists()) {
            return null
        }

        val json = FileReader(logProcessorFile).use(FileReader::readText)
        return logProcessorConfigurationAdapter.fromJson(json)
    }

    override suspend fun getLogConfigurationDefault(): LogConfigurationDto? {
        return resources.openRawResource(R.raw.log_configuration_default).use { inputStream ->
            val json = inputStream.bufferedReader().use(BufferedReader::readText)
            logProcessorConfigurationAdapter.fromJson(json)
        }
    }

    override suspend fun saveLogConfigurationFile(fileDownloaded: FileDownloaded) {
        fileDownloaded.saveFile(logProcessorFile.path)
    }

    companion object {
        const val LAUNCHER_FILE = "/Launcher/configOverride.json"
        const val LOG_PROCESSOR_FILE = "/logprocessor/config.json"
    }
}