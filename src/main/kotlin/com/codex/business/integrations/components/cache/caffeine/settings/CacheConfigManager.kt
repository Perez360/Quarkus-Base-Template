package com.codex.business.integrations.components.cache.caffeine.settings

import io.quarkus.cache.Cache
import io.quarkus.cache.CacheManager
import io.quarkus.cache.CaffeineCache
import jakarta.inject.Singleton
import java.time.Duration
import java.util.*

@Singleton
class CacheConfigManager(private val cacheManager: CacheManager) {
    fun setExpireAfterAccess(cacheName: String, duration: Duration) {
        val cache: Optional<Cache> = cacheManager.getCache(cacheName)
        if (cache.isPresent) {
            cache.get().`as`(CaffeineCache::class.java).setExpireAfterAccess(duration)
        }
    }
}