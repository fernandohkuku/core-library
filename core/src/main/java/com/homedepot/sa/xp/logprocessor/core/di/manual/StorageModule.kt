package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.os.Environment
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl.Companion.LAUNCHER_FILE
import java.io.File


class StorageModule {

    @get:JvmSynthetic
    internal val platformConfigurationFile: File by lazy {
        File(Environment.getExternalStorageDirectory(), LAUNCHER_FILE)
    }

    @get:JvmSynthetic
    internal val logProcessorConfigurationFile: File by lazy {
        File(
            Environment.getExternalStorageDirectory(),
            ConfigurationLocalDataSourceImpl.LOG_PROCESSOR_FILE
        )
    }
}