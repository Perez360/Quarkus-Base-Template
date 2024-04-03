package com.codex.base.exceptions

import com.codex.base.shared.APIResponse
import com.codex.base.utils.wrapErrorInResponse
import com.codex.base.utils.wrapFailureInResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import java.time.DateTimeException
import java.time.format.DateTimeParseException


@Provider
class GlobalExceptionHandler : ExceptionMapper<Exception> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun toResponse(ex: Exception): Response {
        val status: Status
        val errorResponse: APIResponse<String>

        when (ex) {
            is ServiceException -> {
                status = Status.BAD_REQUEST
                errorResponse = wrapFailureInResponse(ex.localizedMessage)
            }

            is IllegalArgumentException -> {
                status = Status.BAD_REQUEST
                errorResponse = wrapFailureInResponse(ex.localizedMessage)
            }

            is DateTimeParseException -> {
                status = Status.BAD_REQUEST
                errorResponse = wrapFailureInResponse(ex.localizedMessage)
            }

            is DateTimeException -> {
                status = Status.BAD_REQUEST
                errorResponse = wrapFailureInResponse(ex.localizedMessage)
            }

            is ConstraintViolationException -> {
                status = Status.BAD_REQUEST
                errorResponse = wrapFailureInResponse(ex.localizedMessage)
            }

            else -> {
                status = Status.INTERNAL_SERVER_ERROR
                errorResponse = wrapErrorInResponse(ex.localizedMessage)
            }
        }


        logger.error("[ HTTP ERROR: GlobalExceptionHandler MESSAGE: {} || STATUS: {}", errorResponse.message, status)

        return Response.status(status).entity(errorResponse).build()
    }
}