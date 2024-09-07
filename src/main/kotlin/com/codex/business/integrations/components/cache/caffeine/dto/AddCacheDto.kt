package com.codex.business.integrations.components.cache.caffeine.dto

import jakarta.validation.constraints.NotBlank


class AddCacheDto<T> {
    @NotBlank(message = "Key field must be provided")
    var key: String? = null

    var value: T? = null

    override fun toString(): String {
        return "AddCacheDto(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }

}