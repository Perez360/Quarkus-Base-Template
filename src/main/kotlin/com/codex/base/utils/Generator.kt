package com.codex.base.utils

import java.util.*

object Generator {
    fun generateSessionId(): String = UUID.randomUUID().toString()
}

