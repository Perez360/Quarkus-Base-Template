package com.codex.business.components.auth.service

import com.codex.base.exceptions.ServiceException
import com.codex.business.components.auth.dto.AddUserDTO
import com.codex.business.components.auth.dto.GetUserTokenDTO
import io.quarkus.keycloak.admin.client.common.KeycloakAdminClientConfig
import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response.Status
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@ApplicationScoped
class AuthServiceImpl : AuthService {
    private val logger: Logger = LoggerFactory.getLogger(AuthServiceImpl::class.java)

    @Inject
    private lateinit var keycloak: Keycloak

    @Inject
    private lateinit var config: KeycloakAdminClientConfig

    override fun getClientToken(): AccessTokenResponse {
        val accessToken = keycloak.tokenManager().grantToken()

        return accessToken
    }

    override fun registerUser(dto: AddUserDTO): UserRepresentation {
        //Check if user already exist with same email
        val listOfUserRepresentations = getUserResource().searchByEmail(dto.email, true)
        if (listOfUserRepresentations.isNotEmpty()) throw ServiceException("User Already exist")

        val userRepresentation = UserRepresentation()
        userRepresentation.username = dto.username
        userRepresentation.firstName = dto.firstname
        userRepresentation.lastName = dto.lastname
        userRepresentation.email = dto.email
        userRepresentation.isEnabled = true


        val credentials = CredentialRepresentation()
        credentials.type = OAuth2Constants.PASSWORD
        credentials.value = dto.password
        credentials.isTemporary = false

        userRepresentation.credentials = listOf(credentials)

        val createResponse = getUserResource().create(userRepresentation)

        logger.debug("Response after registering a user {}", createResponse)

        return if (createResponse.status == Status.CREATED.statusCode)
            userRepresentation else throw ServiceException("Failed to create")
    }

    override fun listRoles(): List<RoleRepresentation> {

        val roles = keycloak.realm(config.realm).roles()
        return roles.list()
    }

    override fun getUserToken(dto: GetUserTokenDTO): AccessTokenResponse {

        val keycloakBuilder = KeycloakBuilder.builder()
            .serverUrl(config.serverUrl.get())
            .realm(config.realm)
            .clientId(config.clientId)
            .clientSecret(config.clientSecret.get())
            .grantType(OAuth2Constants.PASSWORD)
            .username(dto.username)
            .password(dto.password)
            .build()

        val tokenManager = keycloakBuilder.tokenManager()
        logger.info("token manager $tokenManager")
        return keycloakBuilder.tokenManager().accessToken
    }

    private fun getUserResource(): UsersResource = keycloak.realm(config.realm)
        .users()


    @PreDestroy
    fun closeKeycloak() {
        keycloak.close()
    }
}