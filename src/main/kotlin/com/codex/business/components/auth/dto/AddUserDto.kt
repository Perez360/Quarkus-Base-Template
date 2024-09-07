package com.codex.business.components.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class AddUserDto {
    @NotBlank(message = "Username field must be provided")
    val username: String? = null

    @NotBlank(message = "Firstname field must be provided")
    val firstname: String? = null

    @NotBlank(message = "Lastname field must be provided")
    val lastname: String? = null

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email field must be provided")
    val email: String? = null

    @NotBlank(message = "Password field must be provided")
    val password: String? = null
}