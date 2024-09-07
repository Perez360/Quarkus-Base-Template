package com.codex.business.integrations.components.cache.caffeine.dto


data class CacheDto<T>(
    var key: String? = null,
    var value: T? = null
) {
    override fun toString(): String {
        return "CacheDto(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }
}