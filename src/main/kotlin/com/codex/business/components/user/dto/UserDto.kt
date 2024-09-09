package com.codex.business.components.user.dto

import com.codex.business.components.contact.dto.ContactDto
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import java.time.LocalDate
import java.time.LocalDateTime


data class UserDto(
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var dateOfBirth: LocalDate? = null,
    var status: UserStatus? = null,
    var role: UserRole? = null,
    var isEnabled: Boolean? = null,
    var contacts: MutableSet<ContactDto>? = null,
    var createdAt: LocalDateTime? = null,
    var modifiedAt: LocalDateTime? = null,
    var version: Long? = null
) {
    override fun toString(): String {
        return "UserDto(" +
                "id=$id, " +
                "firstName=$firstName, " +
                "lastName=$lastName, " +
                "dateOfBirth=$dateOfBirth, " +
                "status=$status, " +
                "role=$role, " +
                "isEnabled=$isEnabled, " +
                "contacts=$contacts, " +
                "createdAt=$createdAt, " +
                "modifiedAt=$modifiedAt, " +
                "version=$version" +
                ")"
    }
}