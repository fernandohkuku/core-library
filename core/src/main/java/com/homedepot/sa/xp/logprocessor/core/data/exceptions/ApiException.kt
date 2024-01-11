package com.homedepot.sa.xp.logprocessor.core.data.exceptions

import java.io.IOException

open class ApiException(override val message: String) : IOException(message)
