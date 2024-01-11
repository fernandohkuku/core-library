package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.configuration

sealed interface PlatformStateDto {
    data object Loaded : PlatformStateDto
    data object NotLoaded : PlatformStateDto
}