package com.codex.business.integrations.components.cache.caffeine.service

import io.quarkus.cache.Cache
import io.quarkus.cache.CacheName
import io.quarkus.cache.CaffeineCache
import jakarta.enterprise.context.ApplicationScoped
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture


@ApplicationScoped
class CacheService {
    @CacheName("cache")
    private lateinit var cache: Cache

    private val logger: Logger = LoggerFactory.getLogger(CacheService::class.java)

    fun <T> save(key: String, value: T) {
        logger.info("Saving to cache")
        cache.`as`(CaffeineCache::class.java).put(key, CompletableFuture.completedFuture(value))
    }

    fun <T> get(key: String): T? {
        logger.info("Getting from cache")
        return cache.`as`(CaffeineCache::class.java).getIfPresent<T>(key).get()
    }

//    fun <T> getAll(): Map<*, *> {
//        logger.info("Getting from cache")
//        return cache.`as`(CaffeineCache::class.java).
//    }

    fun remove(key: String) {
        logger.info("Saving cache to in memory")
        cache.`as`(CaffeineCache::class.java).invalidate(key)
    }

    fun removeAll() {
        logger.info("Removing all records from incache to in memory")
        cache.`as`(CaffeineCache::class.java).invalidateAll()
    }
}