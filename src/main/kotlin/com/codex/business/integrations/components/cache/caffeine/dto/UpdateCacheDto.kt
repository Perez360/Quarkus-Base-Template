package com.codex.business.integrations.components.cache.caffeine.dto

import jakarta.validation.constraints.NotBlank

class UpdateCacheDto<T> {
    @NotBlank(message = "Key field must be provided")
    var key: String? = null
    var value: T? = null

    override fun toString(): String {
        return "UpdateCacheDto(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }


}