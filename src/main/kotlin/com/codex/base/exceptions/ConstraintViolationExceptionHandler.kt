package com.codex.base.exceptions

import com.codex.base.utils.wrapFailureInResponse
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Provider
class ConstraintViolationExceptionHandler : ExceptionMapper<ConstraintViolationException> {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun toResponse(ex: ConstraintViolationException): Response {
        val status: Status = Status.BAD_REQUEST
        val message: String = ex.constraintViolations.first().message

        logger.error("[ HTTP ERROR: ConstraintViolationExceptionHandler MESSAGE: {} || STATUS: {}", message, status)

        val response = wrapFailureInResponse<String>(message)

        return Response.status(status).entity(response).build()
    }
}