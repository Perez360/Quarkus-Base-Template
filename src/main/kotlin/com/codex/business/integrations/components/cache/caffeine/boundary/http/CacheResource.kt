import com.codex.base.shared.APIResponse
import com.codex.business.integrations.components.cache.caffeine.dto.AddCacheDto
import com.codex.business.integrations.components.cache.caffeine.dto.CacheDto
import com.codex.business.integrations.components.cache.caffeine.dto.UpdateCacheDto
import jakarta.validation.Valid


interface CacheResource {
    /**
     * Add a cache
     * @param dto AddCacheDto<*>
     * @return APIResponse<CacheDto<*>>
     */
    fun addCache(@Valid dto: AddCacheDto<*>): APIResponse<CacheDto<*>>

    /**
     * Update a cache
     * @param dto UpdateCacheDto<*>
     * @return APIResponse<UpdateCacheDto<*>>
     */
    fun updateCache(@Valid dto: UpdateCacheDto<*>): APIResponse<CacheDto<*>>

    /**
     * Get cache by key
     * @param key String
     * @return APIResponse<CacheDto<*>>
     */
    fun getByCacheByKey(key: String): APIResponse<CacheDto<*>>

    /**
     * List caches
     * @param page
     * @param size
     * @return APIResponse<CacheDto<*>>
     */
    fun listAllCaches(page: Int, size: Int): APIResponse<List<CacheDto<*>>>

    /**
     * Delete a cache
     * @param key String
     * @return APIResponse<CacheDto<*>>
     */
    fun deleteCache(key: String): APIResponse<Unit>


    /**
     * Delete all caches
     * @return APIResponse<Unit>
     */
    fun deleteAllCahche(): APIResponse<Unit>
}