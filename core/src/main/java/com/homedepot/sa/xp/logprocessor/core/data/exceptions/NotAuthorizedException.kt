package com.homedepot.sa.xp.logprocessor.core.data.exceptions

class NotAuthorizedException(override val message: String = "Not authorized") : ApiException(message)
