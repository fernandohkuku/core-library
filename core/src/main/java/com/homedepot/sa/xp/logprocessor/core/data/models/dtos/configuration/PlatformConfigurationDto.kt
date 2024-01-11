package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlatformConfigurationDto(
    @get:Json(name = "adminModeEnabled")
    val adminModeEnabled: Boolean,
    @get:Json(name = "maxLocalLogFiles")
    val maxLocalLogFiles: Int,
    @get:Json(name = "maxLocalLogSizeKb")
    val maxLocalLogSizeKb: Int,
    @get:Json(name = "restartOnMaxUptimeEnabled")
    val restartOnMaxUptimeEnabled: Boolean,
    @get:Json(name = "isQAOverride")
    val isQAOverride: Boolean,
    @get:Json(name = "openZoneApplications")
    val openZoneApplications: List<OpenZoneApplicationDto>
)