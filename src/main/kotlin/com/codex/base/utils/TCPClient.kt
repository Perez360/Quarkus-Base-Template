package com.codex.base.utils

import java.net.Socket
import java.nio.charset.StandardCharsets

class TCPClient(addr: String, port: Int) : Socket(addr, port) {
    fun send(msg: String) {
        outputStream.use {
            it.writer(StandardCharsets.UTF_8).write(msg)
        }
    }
}