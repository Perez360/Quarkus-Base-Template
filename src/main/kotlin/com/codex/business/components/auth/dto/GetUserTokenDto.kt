package com.codex.business.components.auth.dto

import jakarta.validation.constraints.NotBlank

class GetUserTokenDto {
    @NotBlank(message = "Username must be provided")
    val username: String? = null

    @NotBlank(message = "Password must be provided")
    val password: String? = null
}