package com.codex.base.utils.keycloak

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class KeycloakConfig(
    val serverUrl: String? = null,
    val clientId: String? = null,
    val secret: String? = null,
    val realm: String? = null,
    val grantType: String? = null
) {
    override fun toString(): String {
        return "KeycloakConfig(" +
                "serverUrl=$serverUrl, " +
                "clientId=$clientId, " +
                "secret=$secret, " +
                "realm=$realm, " +
                "grantType=$grantType" +
                ")"
    }
}