package com.homedepot.sa.xp.logprocessor.core.data.exceptions

class UnProcessableEntityException(
    override val message: String,
) : ApiException(message)