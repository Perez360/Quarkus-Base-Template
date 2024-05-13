package com.codex.business.components.contact.dto

import com.codex.business.components.contact.enum.ContactType
import lombok.ToString
import java.time.LocalDateTime

@ToString
data class ContactDTO(
    var id: String? = null,
    var content: String? = null,
    var type: ContactType? = null,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val version: Long? = null
)