package com.homedepot.sa.xp.logprocessor.core.di.hilt

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.core.net.toUri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {
    @Singleton
    @Provides
    fun provideResources(
        @ApplicationContext context: Context
    ): Resources = context.resources

    @Singleton
    @Provides
    @Named(PACKAGE_NAME)
    fun providePackageName(
        @ApplicationContext context: Context
    ): String = context.packageName

    @Singleton
    @Provides
    @Named(URI_LOG_CONTENT)
    fun provideUriLogContent(): Uri =
        "content://com.homedepot.sa.xp.logprocessor.logs.provider/logs".toUri()

    companion object {
        const val PACKAGE_NAME: String = "packageName"
        const val URI_LOG_CONTENT = "uriLogContent"
    }
}