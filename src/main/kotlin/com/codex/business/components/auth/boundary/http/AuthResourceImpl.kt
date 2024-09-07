package com.codex.business.components.auth.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.base.utils.Mapper
import com.codex.base.utils.wrapSuccessInResponse
import com.codex.business.components.auth.dto.AddUserDto
import com.codex.business.components.auth.dto.GetUserTokenDto
import com.codex.business.components.auth.dto.RoleDto
import com.codex.business.components.auth.service.AuthService
import io.quarkus.security.identity.SecurityIdentity
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.resteasy.reactive.NoCache
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Path("/api/v1/auth")
@Tag(name = "Auth", description = "Manages everything related to auth")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "keycloak")
class AuthResourceImpl : AuthResource {

    @Inject
    private lateinit var authService: AuthService

    @Inject
    private lateinit var identity: SecurityIdentity

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GET
    @Path("/me")
    @NoCache
    override fun me(): APIResponse<String> {
        return wrapSuccessInResponse(identity.principal.name)
    }


    @POST
    @NoCache
    @Path("/user/token")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun getUserToken(dto: GetUserTokenDto): APIResponse<AccessTokenResponse> {
        logger.info("Get user token route has been triggered")
        val accessTokenResponse = authService.getUserToken(dto)
        logger.info("Successfully retrieved user token: {}", accessTokenResponse)
        return wrapSuccessInResponse(accessTokenResponse)
    }

    @GET
    @Path("/roles")
    @RolesAllowed("ADMIN")
    override fun listRoles(): APIResponse<List<RoleDto>> {
        logger.info("Get role route has been triggered")
        val roles = authService.listRoles().map<RoleRepresentation, RoleDto>(Mapper::convert)
        logger.info("Successfully listed roles: {}", roles)
        return wrapSuccessInResponse(roles)
    }

    @POST
    @Path("user/register")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun registerUser(dto: AddUserDto): APIResponse<UserRepresentation> {
        logger.info("Register user route has been triggered")
        val userRepresentation = authService.registerUser(dto)
        logger.info("Successfully registered user: {}", userRepresentation)
        return wrapSuccessInResponse(userRepresentation)
    }
}