package com.codex.business.components.user.boundary.streams

import com.codex.business.components.user.dto.UserDto


interface UserProducer {
    fun publish(dto: UserDto)
}