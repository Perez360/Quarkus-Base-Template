package com.codex.business.components.auth.service

import com.codex.base.exceptions.ServiceException
import com.codex.business.components.auth.dto.AddUserDto
import com.codex.business.components.auth.dto.GetUserTokenDto
import io.quarkus.keycloak.admin.client.common.KeycloakAdminClientConfig
import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response.Status
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.CreatedResponseUtil
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

    override fun registerUser(dto: AddUserDto): UserRepresentation {
        //Check if user already exist with same email
        val listOfUserRepresentations = getUserResource().searchByEmail(dto.email, true)
        if (listOfUserRepresentations.isNotEmpty()) throw ServiceException("User Already exist")

        val userRepresentation = UserRepresentation()
        userRepresentation.username = dto.username
        userRepresentation.firstName = dto.firstname
        userRepresentation.lastName = dto.lastname
        userRepresentation.email = dto.email
        userRepresentation.isEnabled = true


        val createResponse = getUserResource().create(userRepresentation)

        val userId = CreatedResponseUtil.getCreatedId(createResponse)
        setCredential(userId, dto.password!!)

        return if (createResponse.statusInfo.statusCode == Status.CREATED.statusCode)
            getUserResource().get(userId)
                .toRepresentation() else throw ServiceException("Failed to create user: ${createResponse.status}")
    }

    private fun setCredential(userId: String, password: String) {
        val credential = CredentialRepresentation()
        credential.type = OAuth2Constants.PASSWORD
        credential.value = password
        credential.isTemporary = false

        getUserResource()[userId].resetPassword(credential)
    }

    override fun listRoles(): List<RoleRepresentation> {
        val roles = keycloak.realm(config.realm).roles()
        return roles.list()
    }

    override fun getUserToken(dto: GetUserTokenDto): AccessTokenResponse {
        val keycloak = KeycloakBuilder.builder()
            .serverUrl(config.serverUrl.get())
            .realm(config.realm)
            .clientId(config.clientId)
            .clientSecret(config.clientSecret.get())
            .grantType(OAuth2Constants.PASSWORD)
            .username(dto.username)
            .password(dto.password)
            .build()
        return keycloak.tokenManager().accessToken
    }

    private fun getUserResource(): UsersResource = keycloak.realm(config.realm).users()


    @PreDestroy
    fun closeKeycloak() {
        keycloak.close()
    }
}