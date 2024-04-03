package com.codex.business.common.codec

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutput
import java.io.ObjectOutputStream


class GenericCodec<T>(private val cls: Class<T>) : MessageCodec<T, T> {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun encodeToWire(buffer: Buffer?, message: T) {
        if (buffer != null) {
            val bos = ByteArrayOutputStream()
            val out: ObjectOutput?
            try {
                out = ObjectOutputStream(bos)
                out.writeObject(message)
                out.flush()
                val yourBytes = bos.toByteArray()
                buffer.appendInt(yourBytes.size)
                buffer.appendBytes(yourBytes)
                out.close()
            } catch (e: IOException) {

                logger.error("Error occurred whiles encoding to w $")

            } finally {
                try {
                    bos.close()
                } catch (ex: IOException) {
                    logger.error("Error occurred whiles encoding to w user")
                }
            }
        }
    }

    override fun decodeFromWire(p0: Int, p1: Buffer?): T {
        TODO("Not yet implemented")
    }

    override fun name(): String {
        // Each codec must have a unique name.
        // This is used to identify a codec when sending a message and for unregistering
        // codecs.
        return cls.simpleName + "Codec"
    }

    override fun systemCodecID(): Byte {
        TODO("Not yet implemented")
    }

    override fun transform(p0: T): T {
        TODO("Not yet implemented")
    }

}