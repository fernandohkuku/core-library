package com.homedepot.sa.xp.logprocessor.core.di.hilt

import com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi.JsonAdapterFactory
import com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi.JsonAdapterFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MoshiModule {
    @Binds
    fun bindJsonAdapterFactory(factory: JsonAdapterFactoryImpl): JsonAdapterFactory
}