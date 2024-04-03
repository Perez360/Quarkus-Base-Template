package com.codex.business.components

import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.ContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.enum.ContactType
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import com.codex.business.components.user.repo.User
import org.apache.commons.lang3.RandomStringUtils
import java.time.LocalDate
import java.util.*

fun mockAddContactDTO(): AddContactDTO {
    val dto = AddContactDTO()
    dto.userId = UUID.randomUUID().toString()
    dto.content = RandomStringUtils.randomNumeric(10)
    dto.type = ContactType.entries.random()

    return dto
}

fun mockContactDTO(): ContactDTO {
    val dto = ContactDTO()
    dto.id = UUID.randomUUID().toString()
    dto.content = RandomStringUtils.randomNumeric(10)
    dto.type = ContactType.entries.random()
    return dto
}

fun mockUpdateContactDTO(): UpdateContactDTO {
    val dto = UpdateContactDTO()
    dto.id = UUID.randomUUID().toString()
    dto.useId = UUID.randomUUID().toString()
    dto.content = RandomStringUtils.randomNumeric(10)
    dto.type = ContactType.entries.random()
    return dto
}

fun mockUserDTO(): UserDTO {
    val dto = UserDTO()
    dto.id = UUID.randomUUID().toString()
    dto.firstName = RandomStringUtils.randomAlphabetic(20)
    dto.lastName = RandomStringUtils.randomAlphabetic(20)
    dto.role = UserRole.entries.random()
    dto.status = UserStatus.entries.random()
    dto.dob = LocalDate.now().minusYears(20)
    return dto
}

fun mockAddUserDTO(): AddUserDTO {
    val dto = AddUserDTO()
    dto.firstName = RandomStringUtils.randomAlphabetic(20)
    dto.lastName = RandomStringUtils.randomAlphabetic(20)
    dto.role = UserRole.entries.random()
    dto.status = UserStatus.entries.random()
    dto.dob = LocalDate.now().minusYears(20)
    return dto
}


fun mockedUser(): User {
    val user = User()
    user.id = UUID.randomUUID().toString()
    user.firstName = RandomStringUtils.randomAlphabetic(20)
    user.lastName = RandomStringUtils.randomAlphabetic(20)
    user.role = UserRole.entries.random()
    user.status = UserStatus.entries.random()
    user.dob = LocalDate.now().minusYears(20)
    return user
}

fun mockedUpdateUser(): UpdateUserDTO {
    val dto = UpdateUserDTO()
    dto.id = UUID.randomUUID().toString()
    dto.firstName = RandomStringUtils.randomAlphabetic(20)
    dto.lastName = RandomStringUtils.randomAlphabetic(20)
    dto.role = UserRole.entries.random()
    dto.status = UserStatus.entries.random()
    dto.dob = LocalDate.now().minusYears(20)
    return dto
}