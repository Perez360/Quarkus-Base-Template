package com.codex.business.integration.components.sms.dto

data class SmsRequestDTO(
    val message: String,
    val recipients: List<String>
){
    fun toJsonString(): String {
        return """
            {
                "message": "$message",
                "recipients": "$recipients"
            }
        """.trimIndent()
    }
}