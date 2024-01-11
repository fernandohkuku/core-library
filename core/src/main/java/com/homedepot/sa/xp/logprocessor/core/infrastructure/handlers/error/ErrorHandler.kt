package com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error

import java.io.Reader

interface ErrorHandler {
    fun throwFromCode(errorCode: Int, reader: Reader?)
}