package com.homedepot.sa.xp.logprocessor.core.infrastructure.handlers.error

import com.homedepot.sa.xp.logprocessor.core.data.exceptions.BadRequestException
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.NotAuthorizedException
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.NotFoundException
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.ServerException
import com.homedepot.sa.xp.logprocessor.core.data.exceptions.UnProcessableEntityException
import java.io.Reader
import java.net.HttpURLConnection
import javax.inject.Inject

class ApiErrorHandler @Inject constructor() : ErrorHandler {
    companion object {
        private const val HTTP_SYSTEM_CONSTRAINTS = 422
    }

    override fun throwFromCode(errorCode: Int, reader: Reader?) {
        when (errorCode) {
            HttpURLConnection.HTTP_FORBIDDEN -> {
                throw NotAuthorizedException("Not authorized to realize this action.")
            }

            HttpURLConnection.HTTP_BAD_REQUEST ->  {
                throw BadRequestException(message = "Bad request")
            }

            HttpURLConnection.HTTP_NOT_FOUND ->  {
                throw NotFoundException(message = "Not found")
            }
            HTTP_SYSTEM_CONSTRAINTS ->  {
                throw UnProcessableEntityException(message = "Unprocessable error")
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_UNAVAILABLE -> {
                throw ServerException("Sorry, there was a problem in the server. Try again later.")
            }
        }
    }
}