package com.codex.business.components.user.boundary.streams

import com.codex.business.components.user.dto.UserDto
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class UserProducerImpl : UserProducer {

    @Channel("user-out")
    private lateinit var emitter: Emitter<UserDto>

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    override fun publish(dto: UserDto) {
        emitter.send(dto).thenAccept { logger.info("Successfully published user to queue") }
    }
}