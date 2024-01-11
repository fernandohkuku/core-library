package com.homedepot.sa.xp.logprocessor.core.infrastructure.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

interface JsonAdapterFactory {
    fun <T> create(type: Class<T>): JsonAdapter<T>
}

class JsonAdapterFactoryImpl @Inject constructor(private val moshi: Moshi) : JsonAdapterFactory {
    override fun <T> create(type: Class<T>): JsonAdapter<T> {
        return moshi.adapter(type)
    }
}