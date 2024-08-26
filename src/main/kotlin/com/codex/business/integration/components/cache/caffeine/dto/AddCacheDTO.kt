package com.codex.business.integration.components.cache.caffeine.dto

import jakarta.validation.constraints.NotBlank


class AddCacheDTO<T> {
    @NotBlank(message = "Key field must be provided")
    var key: String? = null

    var value: T? = null

    override fun toString(): String {
        return "AddCacheDTO(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }

}