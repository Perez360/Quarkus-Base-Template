package com.codex.base.utils

import io.vertx.core.http.HttpServerRequest
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.Provider
import org.slf4j.LoggerFactory


@Provider
class HttpInterceptor : ContainerRequestFilter, ContainerResponseFilter {
    @Context
    private lateinit var info: UriInfo
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Context
    private lateinit var request: HttpServerRequest

    @Context
    private lateinit var response: HttpServerRequest

    override fun filter(ctx: ContainerRequestContext) {
        val method = ctx.method
        val path = info.path
        val address = request.remoteAddress().toString()

        logger.info("Request {} {} from IP {}", method, path, address)
    }

    override fun filter(p0: ContainerRequestContext?, p1: ContainerResponseContext?) {
        val method = p0?.method
        val path = info.path
        val address = response.remoteAddress().toString()

        val resp = p1?.entity
        logger.info("Response {} {} from IP {} with data: {}", method, path, address, resp)
    }
}