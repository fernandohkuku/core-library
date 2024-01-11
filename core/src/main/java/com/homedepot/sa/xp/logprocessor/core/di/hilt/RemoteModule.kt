package com.homedepot.sa.xp.logprocessor.core.di.hilt

import android.content.Context
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.NetworkInternetException
import com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error.ErrorHandler
import com.homedepot.sa.xp.logprocessor.ui_ktx.content.isInternetAvailable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val INTERCEPTOR_ERROR = "InterceptorError"
private const val INTERCEPTOR_INTERNET_CONNECTION = "InterceptorInternetConnection"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    @Named(INTERCEPTOR_ERROR)
    fun provideInterceptorError(errorHandler: ErrorHandler): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        errorHandler.throwFromCode(response.code(), response.body()?.charStream())
        response
    }

    @Singleton
    @Provides
    @Named(INTERCEPTOR_INTERNET_CONNECTION)
    fun provideInterceptorInternetConnection(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            if (!context.isInternetAvailable()) {
                throw NetworkInternetException()
            }
            chain.proceed(chain.request())
        }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideMoshiConvertFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        @Named(INTERCEPTOR_ERROR) interceptorError: Interceptor,
        @Named(INTERCEPTOR_INTERNET_CONNECTION) interceptorInternetConnection: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptorError)
        addInterceptor(interceptorInternetConnection)
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .baseUrl("https://example.com/v1/")
            .client(okHttpClient)

    @Singleton
    @Provides
    fun provideRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit = retrofitBuilder.build()
}