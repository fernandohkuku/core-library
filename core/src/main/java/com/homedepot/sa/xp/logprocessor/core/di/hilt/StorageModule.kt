package com.homedepot.sa.xp.logprocessor.core.di.hilt

import android.os.Environment
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl.Companion.LAUNCHER_FILE
import com.homedepot.sa.xp.logprocessor.core.data.local.source.configuration.ConfigurationLocalDataSourceImpl.Companion.LOG_PROCESSOR_FILE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

const val PLATFORM_CONFIG = "platformConfig"
const val LOG_PROCESSOR_CONFIG = "logProcessorConfig"


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Singleton
    @Provides
    @Named(PLATFORM_CONFIG)
    fun providePlatformConfigurationFile(): File =
        File(Environment.getExternalStorageDirectory(), LAUNCHER_FILE)

    @Singleton
    @Provides
    @Named(LOG_PROCESSOR_CONFIG)
    fun provideLogProcessorConfigFile(): File =
        File(Environment.getExternalStorageDirectory(), LOG_PROCESSOR_FILE)
}