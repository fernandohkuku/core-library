package com.homedepot.sa.xp.logprocessor.core.di.hilt

import com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error.ApiErrorHandler
import com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorModule {
    @Binds
    fun bindErrorHandler(
        errorHandler: ApiErrorHandler
    ): ErrorHandler
}