package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import android.content.res.Configuration
import com.homedepot.sa.xp.logprocessor.core.data.remote.api.configurator.ConfiguratorService
import com.homedepot.sa.xp.logprocessor.core.data.remote.source.ConfigurationRemoteDataSourceImpl
import com.homedepot.sa.xp.logprocessor.core.data.repositories.ConfigurationRemoteDataSource
import com.homedepot.sa.xp.logprocessor.core.domain.repositories.ConfigurationRepository


interface AppModule {
    val configurationManager: ConfigurationRepository
}


class AppModuleImpl(
    private val context: Context
) : AppModule {

    private val remoteModule by lazy {
        RemoteModule(context)
    }

    private val storageModule by lazy { StorageModule() }

    private val presentationModule by lazy {
        PresentationModule(context)
    }

    private val moshiAdapterModule by lazy {
        MoshiAdapterModule(remoteModule.moshi)
    }

    private val remoteDataModule by lazy { RemoteDataModule(remoteModule.retrofit) }

    private val localDataModule by lazy {
        LocalDataModule(
            resources = presentationModule.resources,
            platformConfigFile = storageModule.platformConfigurationFile,
            logProcessorFile = storageModule.logProcessorConfigurationFile,
            logProcessorConfigurationAdapter = moshiAdapterModule.logProcessorConfigurationAdapter,
            platformConfigurationAdapter = moshiAdapterModule.platformConfigurationAdapter
        )
    }

    private val dataModule by lazy {
        DataModule(
            configurationLocalDataSource = localDataModule.configurationLocalDataSource,
            configurationRemoteDataSource = remoteDataModule.configurationRemoteDataSource
        )
    }
    override val configurationManager: ConfigurationRepository
        get() = dataModule.configurationManager
}