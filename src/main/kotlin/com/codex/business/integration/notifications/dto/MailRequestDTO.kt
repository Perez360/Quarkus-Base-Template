package com.codex.business.integration.notifications.dto

data class MailRequestDTO(
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

data class MailResponseDTO(
    val code: String,
    val message: String,
)