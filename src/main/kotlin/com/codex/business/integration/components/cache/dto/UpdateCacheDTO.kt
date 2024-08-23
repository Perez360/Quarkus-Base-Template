package com.codex.business.integration.components.cache.dto

import jakarta.validation.constraints.NotBlank

class UpdateCacheDTO<T> {
    @NotBlank(message = "Key field must be provided")
    var key: String? = null
    var value: T? = null

    override fun toString(): String {
        return "UpdateCacheDTO(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }


}