package com.codex.base.utils.keycloak

import io.quarkus.test.common.QuarkusTestResource
import jakarta.inject.Inject
import org.keycloak.admin.client.KeycloakBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@QuarkusTestResource(KeycloakTestResourse::class, restrictToAnnotatedClass = false)
open class AccessTokenProvider {
    @Inject
    protected lateinit var config: KeycloakConfig
    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)


    protected fun getAccessToken(userName: String, password: String): String {

        logger.info("Keycloak config received: $config")

        return KeycloakBuilder.builder()
            .serverUrl(config.serverUrl)
            .realm(config.realm)
            .clientId(config.clientId)
            .clientSecret(config.secret)
            .grantType(config.grantType)
            .username(userName)
            .password(password)
            .build().tokenManager().accessTokenString
    }
}