package com.codex.business.integrations.notifications.dto

data class MailRequestDto(
    val message: String,
    val subject:
    String,
    val recipients: List<String>
) {
    fun toJsonString(): String {
        return """
            {
                "message": "$message",
                "subject": "$subject",
                "recipients": [${recipients.joinToString(",") { "\"$it\"" }}],
            }
        """.trimIndent()
    }
}

data class MailResponseDto(
    val code: String,
    val message: String,
)