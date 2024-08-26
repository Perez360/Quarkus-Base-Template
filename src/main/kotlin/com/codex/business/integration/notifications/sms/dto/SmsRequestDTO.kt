package com.codex.business.integration.notifications.sms.dto

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