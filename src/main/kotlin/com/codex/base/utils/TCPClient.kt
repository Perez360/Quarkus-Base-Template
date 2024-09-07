package com.codex.base.utils

import java.net.Socket
import java.nio.charset.StandardCharsets

class TCPClient(address: String, port: Int) : Socket(address, port) {
    fun send(msg: String) {
        outputStream.use { stream ->
            stream.writer(StandardCharsets.UTF_8).write(msg)
        }
    }
}