package com.codex.business.integration.components.cache.boundary.http

import CacheResource
import com.codex.base.shared.APIResponse
import com.codex.base.utils.Mapper
import com.codex.base.utils.wrapSuccessInResponse
import com.codex.business.integration.components.cache.CacheService
import com.codex.business.integration.components.cache.dto.AddCacheDTO
import com.codex.business.integration.components.cache.dto.CacheDTO
import com.codex.business.integration.components.cache.dto.UpdateCacheDTO
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity_.id
import io.quarkus.security.Authenticated
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Authenticated
@Path("/api/v1/caches")
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "keycloak")
@Tag(name = "Caches", description = "Manages everything related to caches")
@ApplicationScoped
class CacheResourceImpl : CacheResource {

    @Inject
    private lateinit var cacheService: CacheService

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    @POST
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun addCache(dto: AddCacheDTO<*>): APIResponse<CacheDTO<*>> {
        logger.info("Add cache route has been triggered with dto: {}", dto)
        cacheService.save(dto.key!!, dto.value)
        val oneCacheDTO = Mapper.convert<AddCacheDTO<*>, CacheDTO<*>>(dto)
        logger.info("Successfully added cached: {}", oneCacheDTO)
        return wrapSuccessInResponse(oneCacheDTO)
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun updateCache(dto: UpdateCacheDTO<*>): APIResponse<CacheDTO<*>> {
        logger.info("Update user route has been triggered with dto: {}", dto)
        cacheService.save(dto.key!!, dto.value)
        val oneCacheDTO = Mapper.convert<UpdateCacheDTO<*>, CacheDTO<*>>(dto)
        logger.info("Successfully updated user: {}", oneCacheDTO)
        return wrapSuccessInResponse(oneCacheDTO)
    }


    @GET
    @Path("/{key}")
    @RolesAllowed("ADMIN", "USER")
    override fun getByCacheByKey(@PathParam("key") key: String): APIResponse<CacheDTO<*>> {
        logger.info("Get cache by key route has been triggered with key: {}", key)
        val oneCache = cacheService.get<String>(key)
        logger.info("Cache data: {}", oneCache)
        return wrapSuccessInResponse(CacheDTO(key = key, value = oneCache))
    }

    @GET
    @Path("/list")
    @RolesAllowed("ADMIN", "USER")
    override fun listAllCaches(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int
    ): APIResponse<List<CacheDTO<*>>> {
        logger.info("List users route has been triggered with page: {} and size: {}", page, size)
        logger.info("Listed caches in pages: {}")
        return wrapSuccessInResponse(listOf())
    }

    @DELETE
    @Path("/{key}")
    @RolesAllowed("ADMIN")
    override fun deleteCache(@PathParam("key") key: String): APIResponse<Unit> {
        logger.info("Delete cache route has been triggered with id: {}", key)
        cacheService.remove(key)
        logger.info("Successfully deleted cache")
        return wrapSuccessInResponse(Unit)
    }

    @DELETE
    @RolesAllowed("ADMIN")
    override fun deleteAllCahche(): APIResponse<Unit> {
        logger.info("Delete cache route has been triggered with id: {}", id)
        cacheService.removeAll()
        logger.info("{}: Successfully deleted all cache")
        return wrapSuccessInResponse(Unit)
    }
}