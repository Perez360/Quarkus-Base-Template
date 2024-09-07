package com.codex.business.components.user.dto

import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate


class AddUserDto {
    @NotBlank(message = "Firstname field must be provided")
    var firstName: String? = null

    @NotBlank(message = "Lastname field must be provided")
    var lastName: String? = null

    @NotNull(message = "Date of birth field must not be null")
    @PastOrPresent(message = "Date cannot be greater the current date")
    var dateOfBirth: LocalDate? = null
    var status: UserStatus = UserStatus.ALIVE
    var role: UserRole = UserRole.USER


    override fun toString(): String {
        return "AddUserDto(" +
                "firstName=$firstName, " +
                "lastName=$lastName, " +
                "dateOfBirth=$dateOfBirth, " +
                "status=$status, " +
                "role=$role" +
                ")"
    }
}