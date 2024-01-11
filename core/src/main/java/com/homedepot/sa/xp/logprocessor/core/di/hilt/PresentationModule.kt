package com.homedepot.sa.xp.logprocessor.core.di.hilt

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {
    @Singleton
    @Provides
    fun provideResources(
        @ApplicationContext context: Context
    ): Resources = context.resources
}