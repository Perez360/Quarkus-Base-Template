package com.codex.business.components.contact.dto

import com.codex.business.components.contact.enum.ContactType
import java.time.LocalDateTime

data class ContactDTO(
    var id: String? = null,
    var content: String? = null,
    var type: ContactType? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val version: Long? = null
)