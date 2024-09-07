package com.codex.base.utils

import com.codex.base.Texts.CODE_ERROR
import com.codex.base.Texts.CODE_FAILURE
import com.codex.base.Texts.CODE_SUCCESS
import com.codex.base.Texts.SERVER_CODE_ERROR
import com.codex.base.Texts.SYSTEM_CODE_FAILURE
import com.codex.base.Texts.SYSTEM_CODE_SUCCESS
import com.codex.base.Texts.SYSTEM_MESSAGE_ERROR
import com.codex.base.Texts.SYSTEM_MESSAGE_FAILURE
import com.codex.base.Texts.SYSTEM_MESSAGE_SUCCESS
import com.codex.base.shared.APIResponse

fun <T> wrapSuccessInResponse(data: T): APIResponse<T> {
    return APIResponse(CODE_SUCCESS, SYSTEM_CODE_SUCCESS, SYSTEM_MESSAGE_SUCCESS, "Success", data)
}

fun <T> wrapFailureInResponse(message: String?): APIResponse<T> {
    return APIResponse(CODE_FAILURE, SYSTEM_CODE_FAILURE, SYSTEM_MESSAGE_FAILURE, message, null)
}

fun <T> wrapErrorInResponse(message: String?): APIResponse<T> {
    return APIResponse(CODE_ERROR, SERVER_CODE_ERROR, SYSTEM_MESSAGE_ERROR, message, null)
}