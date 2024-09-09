package com.codex.business.components.user.boundary.streams

import io.smallrye.reactive.messaging.annotations.Blocking
import io.vertx.core.json.JsonObject
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletionStage

class UserConsumer {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Blocking
    @Incoming("user-in")
    fun consume(msg: Message<JsonObject>): CompletionStage<Void> {
//        Thread.sleep(3000)
        logger.info("Received user successfully from the queue: ${msg.payload}")
        return msg.ack()
    }
}