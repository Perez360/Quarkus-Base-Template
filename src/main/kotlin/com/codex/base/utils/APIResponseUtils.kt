package com.codex.base.utils

import com.codex.base.*
import com.codex.base.shared.APIResponse

fun <T> wrapSuccessInResponse(data: T): APIResponse<T> {
    return APIResponse(CODE_SUCCESS, SYSTEM_CODE_SUCCESS, SYSTEM_MESSAGE_SUCCESS, "Success", data)
}

fun <T> wrapFailureInResponse(message: String): APIResponse<T> {
    return APIResponse(CODE_FAILURE, SYSTEM_CODE_FAILURE, SYSTEM_MESSAGE_FAILURE, message, null)
}

fun <T> wrapErrorInResponse(message: String): APIResponse<T> {
    return APIResponse(CODE_ERROR, SERVER_CODE_ERROR, message, SYSTEM_MESSAGE_ERROR, null)
}