package com.codex.business.integration.components.cache.caffeine.dto


data class CacheDTO<T>(
    var key: String? = null,
    var value: T? = null
) {
    override fun toString(): String {
        return "CacheDTO(" +
                "key=$key, " +
                "value=$value" +
                ")"
    }
}