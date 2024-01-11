package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivationSysParmDto(
    @get:Json(name = "sysParmName")
    val sysParmName: String,
    @get:Json(name = "sysParmSubSysCd")
    val sysParmSubSysCd: String,
    @get:Json(name = "activationValue")
    val activationValue: String,
)