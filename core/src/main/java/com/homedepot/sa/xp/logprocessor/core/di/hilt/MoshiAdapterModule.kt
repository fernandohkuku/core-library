package com.homedepot.sa.xp.logprocessor.core.di.hilt

import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.LogConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration.PlatformConfigurationDto
import com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi.JsonAdapterFactory
import com.squareup.moshi.JsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoshiAdapterModule {

    @Provides
    @Singleton
    fun providePlatformConfigurationAdapter(
        jsonAdapterFactory: JsonAdapterFactory
    ): JsonAdapter<PlatformConfigurationDto> =
        jsonAdapterFactory.create(PlatformConfigurationDto::class.java)

    @Provides
    @Singleton
    fun provideLogProcessorConfigurationAdapter(
        jsonAdapterFactory: JsonAdapterFactory
    ): JsonAdapter<LogConfigurationDto> =
        jsonAdapterFactory.create(LogConfigurationDto::class.java)
}