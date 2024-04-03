package com.codex.base.shared

data class APIResponse<T>(
    val code: String,
    val systemCode: String,
    val systemMessage: String,
    val message: String,
    val data: T?
)