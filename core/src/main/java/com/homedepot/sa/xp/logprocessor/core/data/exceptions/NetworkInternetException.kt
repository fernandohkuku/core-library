package com.homedepot.sa.xp.logprocessor.core.data.exceptions

class NetworkInternetException(
    override val message: String = "the internet connection is not available",
) : ApiException(message)