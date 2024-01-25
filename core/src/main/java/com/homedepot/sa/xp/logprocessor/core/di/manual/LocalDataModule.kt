package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import com.homedepot.sa.xp.logprocessor.core.data.local.database.dao.LogDao
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.local.source.log.LogsLocalDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.log.LogDto
import com.squareup.moshi.JsonAdapter
import java.io.File

internal class LocalDataModule(
    private val context: Context,
    private val resources: Resources,
    private val logContent: Uri,
    private val platformConfigFile: File,
    private val logProcessorFile: File,
    private val logProcessorConfigurationAdapter: JsonAdapter<LogConfigurationDto>,
    private val platformConfigurationAdapter: JsonAdapter<PlatformConfigurationDto>,
    private val logAdapter: JsonAdapter<LogDto>,
    private val logDao: LogDao
) {

    @get:JvmSynthetic
    val configurationLocalDataSource by lazy {
        ConfigurationLocalDataSourceImpl(
            resources = resources,
            platformConfigFile = platformConfigFile,
            logProcessorFile = logProcessorFile,
            logProcessorConfigurationAdapter = logProcessorConfigurationAdapter,
            platformConfigurationAdapter = platformConfigurationAdapter
        )
    }

    @get:JvmSynthetic
    val logLocalDataSource by lazy {
        LogsLocalDataSourceImpl(
            context = context,
            logAdapter = logAdapter,
            logDao = logDao,
            logContent = logContent
        )
    }
}