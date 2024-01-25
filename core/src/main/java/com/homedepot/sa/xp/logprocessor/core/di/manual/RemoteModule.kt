package com.homedepot.sa.xp.logprocessor.core.di.manual

import android.content.Context
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.NetworkInternetException
import com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error.ApiErrorHandler
import com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error.ErrorHandler
import com.homedepot.sa.xp.logprocessor.ui_ktx.content.isInternetAvailable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RemoteModule(
    private val context: Context
) {

    @get:JvmSynthetic
    private val errorHandler: ErrorHandler by lazy {
        ApiErrorHandler()
    }

    @get:JvmSynthetic
    private val interceptorError: Interceptor by lazy {
        Interceptor { chain ->
            val response = chain.proceed(chain.request())
            errorHandler.throwFromCode(response.code(), response.body()?.charStream())
            response
        }
    }

    @get:JvmSynthetic
    private val interceptorInternetConnection: Interceptor by lazy {
        Interceptor { chain ->
            if (!context.isInternetAvailable()) {
                throw NetworkInternetException()
            }
            chain.proceed(chain.request())
        }
    }

    @get:JvmSynthetic
    val moshi: Moshi by lazy {
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @get:JvmSynthetic
    private val moshiFactory: MoshiConverterFactory by lazy {
        MoshiConverterFactory.create(moshi)
    }

    @get:JvmSynthetic
    private val okHttpClientBuilder: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            addInterceptor(interceptorError)
            addInterceptor(interceptorInternetConnection)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @get:JvmSynthetic
    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .addConverterFactory(moshiFactory)
            .baseUrl("https://example.com/v1/")
            .client(okHttpClientBuilder)
    }

    @get:JvmSynthetic
    val retrofit: Retrofit by lazy {
        retrofitBuilder.build()
    }
}