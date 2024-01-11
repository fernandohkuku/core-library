package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.res.Resources
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.squareup.moshi.JsonAdapter
import java.io.File

internal class LocalDataModule(
    private val resources: Resources,
    private val platformConfigFile: File,
    private val logProcessorFile: File,
    private val logProcessorConfigurationAdapter: JsonAdapter<LogConfigurationDto>,
    private val platformConfigurationAdapter: JsonAdapter<PlatformConfigurationDto>
) {

    val configurationLocalDataSource by lazy {
        ConfigurationLocalDataSourceImpl(
            resources = resources,
            platformConfigFile = platformConfigFile,
            logProcessorFile = logProcessorFile,
            logProcessorConfigurationAdapter = logProcessorConfigurationAdapter,
            platformConfigurationAdapter = platformConfigurationAdapter
        )
    }
}