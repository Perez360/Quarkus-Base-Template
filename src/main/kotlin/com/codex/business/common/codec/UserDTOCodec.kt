package com.codex.business.common.codec

import com.codex.business.components.user.dto.UserDTO
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.Json

class UserDTOCodec : MessageCodec<UserDTO, UserDTO> {
    override fun encodeToWire(p0: Buffer?, p1: UserDTO?) {

        // Encode object to string
        val jsonToStr: String = Json.encode(p0)

        // Length of JSON: is NOT characters count
        val length = jsonToStr.toByteArray().size

        // Write data into given buffer
        p0?.appendInt(length)
        p0?.appendString(jsonToStr)
    }

    override fun decodeFromWire(p0: Int, p1: Buffer?): UserDTO {
        return Json.decodeValue(p1, UserDTO::class.java)
    }

    override fun transform(p0: UserDTO): UserDTO {
        // If a message is sent *locally* across the event bus.
        // This example sends message just as is
        return p0
    }

    override fun name(): String {
        // Each codec must have a unique name.
        // This is used to identify a codec when sending a message and for unregistering codecs.
        return this::class.java.simpleName
    }

    // Always -1
    override fun systemCodecID(): Byte = -1
}