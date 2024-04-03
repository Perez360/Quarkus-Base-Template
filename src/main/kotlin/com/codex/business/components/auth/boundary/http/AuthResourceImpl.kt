package com.codex.business.components.auth.boundary.http
//
//import com.codex.base.shared.APIResponse
//import com.codex.base.utils.SessionGenerator
//import com.codex.base.utils.wrapFailureInResponse
//import com.codex.base.utils.wrapSuccessInResponse
//import com.codex.business.components.auth.boundary.AuthResource
//import com.codex.business.components.auth.dto.AddUserDTO
//import com.codex.business.components.auth.dto.GetUserTokenDTO
//import com.codex.business.components.auth.service.AuthService
//import io.quarkus.security.Authenticated
//import io.quarkus.security.identity.SecurityIdentity
//import jakarta.annotation.security.PermitAll
//import jakarta.inject.Inject
//import jakarta.ws.rs.*
//import jakarta.ws.rs.core.MediaType
//import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
//import org.eclipse.microprofile.openapi.annotations.tags.Tag
//import org.jboss.resteasy.reactive.NoCache
//import org.keycloak.representations.AccessTokenResponse
//import org.keycloak.representations.idm.RoleRepresentation
//import org.keycloak.representations.idm.UserRepresentation
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import java.security.Principal
//
//
//@Path("/api/v1/auth")
//@Tag(name = "Auth", description = "Manages everything related to auth")
//@Produces(MediaType.APPLICATION_JSON)
//@SecurityRequirement(name = "keycloak")
//@PermitAll
//class AuthResourceImpl : AuthResource {
//
//    @Inject
//    private lateinit var authService: AuthService
//
//    @Inject
//    private lateinit var identity: SecurityIdentity
//
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    @GET
//    @Path("/me")
//    @NoCache
//
//    override fun me(): APIResponse<Principal> {
//        return wrapSuccessInResponse(identity.principal)
//    }
//
//    @GET
//    @NoCache
//    @Path("/client/token")
//    override fun getClientToken(): APIResponse<AccessTokenResponse> {
//        logger.info("Get client token route has been triggered")
//        val sessionId = SessionGenerator.generateSessionID()
//        val accessTokenResponse = authService.getClientToken()
//        logger.info("{}: Successfully retrieved client token: {}", sessionId, accessTokenResponse)
//        return wrapSuccessInResponse(accessTokenResponse)
//    }
//
//    @POST
//    @NoCache
//    @Path("/user/token")
//    @Consumes(MediaType.APPLICATION_JSON)
//    override fun getUserToken(dto: GetUserTokenDTO): APIResponse<AccessTokenResponse> {
//        logger.info("Get user token route has been triggered")
//        val sessionId = SessionGenerator.generateSessionID()
//        val accessTokenResponse = authService.getClientToken()
//        logger.info("{}: Successfully retrieved user token: {}", sessionId, accessTokenResponse)
//        return wrapSuccessInResponse(accessTokenResponse)
//    }
//
//    @GET
//    @NoCache
//    @Path("/roles")
//    override fun listRoles(): APIResponse<List<RoleRepresentation>> {
//        logger.info("Get role route has been triggered")
//        val sessionId = SessionGenerator.generateSessionID()
//        val accessTokenResponse = authService.listRoles()
//        logger.info("{}: Successfully listed roles: {}", sessionId, accessTokenResponse)
//        val roles = authService.listRoles()
//        return wrapSuccessInResponse(roles)
//    }
//
//    @POST
//    @Path("user/register")
//    @Consumes(MediaType.APPLICATION_JSON)
//    override fun registerUser(dto: AddUserDTO): APIResponse<UserRepresentation> {
//
//        logger.info("Register user route has been triggered")
//        val sessionId = SessionGenerator.generateSessionID()
//        val userRepresentation = authService.registerUser(dto)
//
//        return if (userRepresentation != null) {
//            logger.info("{}: Successfully registered user: {}", sessionId, userRepresentation)
//            wrapSuccessInResponse(userRepresentation)
//        } else {
//            logger.error("{}: Failed to registered user", sessionId)
//            wrapFailureInResponse("Failed to register user")
//        }
//    }
//}