package com.codex.business.components.user.dto

import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

class UpdateUserDTO {
    @NotBlank(message = "Id field must be provided")
    var id: String? = null

    @NotBlank(message = "Firstname field must be provided")
    var firstName: String? = null

    @NotBlank(message = "Lastname field must be provided")
    var lastName: String? = null

    @NotNull(message = "Date of birth field must be a valid date")
    @PastOrPresent(message = "Date cannot be greater the current date")
    var dateOfBirth: LocalDate? = null

    @NotNull(message = "Status field must be provided")
    var status: UserStatus? = null

    @NotNull(message = "Role field must be provided")
    var role: UserRole? = null
}