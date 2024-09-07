package com.codex.business.components.contact.dto

import com.codex.business.components.contact.enum.ContactType
import java.time.LocalDateTime

data class ContactDto(
    var id: String? = null,
    var content: String? = null,
    var type: ContactType? = null,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val version: Long? = null

) {
    override fun toString(): String {
        return "ContactDto(" +
                "id=$id, " +
                "content=$content, " +
                "type=$type, " +
                "createdAt=$createdAt, " +
                "modifiedAt=$modifiedAt, " +
                "version=$version" +
                ")"
    }
}