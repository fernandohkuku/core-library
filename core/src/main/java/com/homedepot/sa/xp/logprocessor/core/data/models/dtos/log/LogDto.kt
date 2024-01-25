package com.homedepot.sa.xp.logprocessor.core.data.models.dtos.log

data class LogDto(
    val level: Int,
    val message: String,
    val tag: String
)