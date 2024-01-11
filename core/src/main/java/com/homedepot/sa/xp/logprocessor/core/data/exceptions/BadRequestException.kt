package com.homedepot.sa.xp.logprocessor.core.data.exceptions

class BadRequestException(
    override val message: String = "Bad request"
) : ApiException(message)