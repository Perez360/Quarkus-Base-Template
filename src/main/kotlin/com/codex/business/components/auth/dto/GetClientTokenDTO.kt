package com.codex.business.components.auth.dto

import jakarta.validation.constraints.NotBlank

class GetClientTokenDTO {
    @NotBlank(message = "Client id must be provided")
    val clientId: String? = null

    @NotBlank(message = "Client secret must be provided")
    val clientSecret: String? = null
}