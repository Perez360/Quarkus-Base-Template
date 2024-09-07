package com.codex.business.components.user.boundary.streams

import com.codex.business.components.user.dto.UserDto
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class UserProducer {

    @Channel("user-channel")
    private lateinit var emitter: Emitter<UserDto>

    fun publish(dto: UserDto) {
        emitter.send(dto)
    }
}