package com.homedepot.sa.xp.logprocessor.core.di.manual

import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi.JsonAdapterFactory
import com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi.JsonAdapterFactoryImpl
import com.squareup.moshi.Moshi

class MoshiAdapterModule(
    private val moshi: Moshi
) {

    private val jsonAdapterFactory: JsonAdapterFactory by lazy {
        JsonAdapterFactoryImpl(moshi)
    }

    val platformConfigurationAdapter by lazy {
        jsonAdapterFactory.create(PlatformConfigurationDto::class.java)
    }

    val logProcessorConfigurationAdapter by lazy {
        jsonAdapterFactory.create(LogConfigurationDto::class.java)
    }

}