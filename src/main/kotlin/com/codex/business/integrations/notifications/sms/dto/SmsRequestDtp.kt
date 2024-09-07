package com.codex.business.integrations.notifications.sms.dto

data class SmsRequestDtp(
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