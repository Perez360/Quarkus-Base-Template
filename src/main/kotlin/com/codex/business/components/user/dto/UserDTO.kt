package com.codex.business.components.user.dto

import com.codex.business.components.contact.dto.ContactDTO
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import lombok.ToString
import java.time.LocalDate
import java.time.LocalDateTime


@ToString
data class UserDTO(
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var dob: LocalDate? = null,
    var status: UserStatus? = null,
    var role: UserRole? = null,
    val contacts: MutableSet<ContactDTO>? = null,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val version: Long? = null
)