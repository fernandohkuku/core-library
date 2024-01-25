package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SaveLogUseCase
import com.homedepot.sa.xp.logprocessor.core.domain.usecases.log.SendLogsUseCase


interface AppModule {
    val saveLogUseCase: SaveLogUseCase
    val sendLogsUseCase: SendLogsUseCase
}


class AppModuleImpl(
    private val context: Context
) : AppModule {
    @get:JvmSynthetic
    private val dispatcherModule by lazy {
        DispatcherModule()
    }

    @get:JvmSynthetic
    private val dataBaseModule by lazy {
        DataBaseModule(context)
    }

    @get:JvmSynthetic
    private val remoteModule by lazy {
        RemoteModule(context)
    }

    @get:JvmSynthetic
    private val storageModule by lazy { StorageModule() }

    @get:JvmSynthetic
    private val presentationModule by lazy {
        PresentationModule(context)
    }

    @get:JvmSynthetic
    private val moshiAdapterModule by lazy {
        MoshiAdapterModule(remoteModule.moshi)
    }

    @get:JvmSynthetic
    private val remoteDataModule by lazy { RemoteDataModule(remoteModule.retrofit) }

    @get:JvmSynthetic
    private val localDataModule by lazy {
        LocalDataModule(
            context = context,
            resources = presentationModule.resources,
            platformConfigFile = storageModule.platformConfigurationFile,
            logProcessorFile = storageModule.logProcessorConfigurationFile,
            logProcessorConfigurationAdapter = moshiAdapterModule.logProcessorConfigurationAdapter,
            platformConfigurationAdapter = moshiAdapterModule.platformConfigurationAdapter,
            logAdapter = moshiAdapterModule.logAdapter,
            logDao = dataBaseModule.logDao,
            logContent = presentationModule.uriLogContent
        )
    }

    @get:JvmSynthetic
    private val dataModule by lazy {
        DataModule(
            configurationLocalDataSource = localDataModule.configurationLocalDataSource,
            configurationRemoteDataSource = remoteDataModule.configurationRemoteDataSource,
            logLocalDataSource = localDataModule.logLocalDataSource
        )
    }

    @get:JvmSynthetic
    private val domainModule by lazy {
        DomainModule(
            background = dispatcherModule.dispatcherIO, logRepository = dataModule.logRepository
        )
    }

    @get:JvmSynthetic
    override val saveLogUseCase: SaveLogUseCase by lazy {
        domainModule.saveLogUseCase
    }

    @get:JvmSynthetic
    override val sendLogsUseCase: SendLogsUseCase by lazy {
        domainModule.sendLogsUseCase
    }
}