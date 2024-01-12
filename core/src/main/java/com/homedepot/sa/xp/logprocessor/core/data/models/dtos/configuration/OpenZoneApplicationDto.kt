package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenZoneApplicationDto(
    @get:Json(name = "appName")
    val appName: String,
    @get:Json(name = "appDesc")
    val appDesc: String,
    @get:Json(name = "appArgs")
    val appArgs: String,
    @get:Json(name = "appPath")
    val appPath: String,
    @get:Json(name = "iconIndex")
    val iconIndex: Int,
    @get:Json(name = "pinApplication")
    val pinApplication: Boolean,
    @get:Json(name = "activationSysParm")
    val activationSysParm: ActivationSysParmDto
)
