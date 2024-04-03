package com.codex.base.exceptions


class ServiceException(override val message: String?, override val cause: Throwable?) :
    RuntimeException(message, cause) {
    constructor(message: String?) : this(message, null)
}
