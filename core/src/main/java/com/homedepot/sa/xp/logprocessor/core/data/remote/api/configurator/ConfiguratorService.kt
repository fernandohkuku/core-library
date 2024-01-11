package com.homedepot.sa.xp.logprocessor.core.data.remote.api.configurator

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ConfiguratorService {
    @GET
    @Streaming
    fun downloadConfigurator(
        @Url file: String
    ): ResponseBody
}