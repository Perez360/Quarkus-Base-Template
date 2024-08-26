import com.codex.base.shared.APIResponse
import com.codex.business.integration.components.cache.caffeine.dto.AddCacheDTO
import com.codex.business.integration.components.cache.caffeine.dto.CacheDTO
import com.codex.business.integration.components.cache.caffeine.dto.UpdateCacheDTO
import jakarta.validation.Valid


interface CacheResource {
    /**
     * Add a cache
     * @param dto AddCacheDTO<*>
     * @return APIResponse<CacheDTO<*>>
     */
    fun addCache(@Valid dto: AddCacheDTO<*>): APIResponse<CacheDTO<*>>

    /**
     * Update a cache
     * @param dto UpdateCacheDTO<*>
     * @return APIResponse<UpdateCacheDTO<*>>
     */
    fun updateCache(@Valid dto: UpdateCacheDTO<*>): APIResponse<CacheDTO<*>>

    /**
     * Get cache by key
     * @param key String
     * @return APIResponse<CacheDTO<*>>
     */
    fun getByCacheByKey(key: String): APIResponse<CacheDTO<*>>

    /**
     * List caches
     * @param page
     * @param size
     * @return APIResponse<CacheDTO<*>>
     */
    fun listAllCaches(page: Int, size: Int): APIResponse<List<CacheDTO<*>>>

    /**
     * Delete a cache
     * @param key String
     * @return APIResponse<CacheDTO<*>>
     */
    fun deleteCache(key: String): APIResponse<Unit>


    /**
     * Delete all caches
     * @return APIResponse<Unit>
     */
    fun deleteAllCahche(): APIResponse<Unit>
}