package com.codex.base.utils.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager.TestInjector
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager.TestInjector.AnnotatedAndMatchesType
import jakarta.inject.Inject
import org.keycloak.OAuth2Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap


class KeycloakTestResource : QuarkusTestResourceLifecycleManager {
    private val contextPath = "/realms/codex"
    private lateinit var keycloakContainer: KeycloakContainer
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    override fun start(): MutableMap<String, String> {
        logger.info("Starting keycloak test lifecycle...")


        keycloakContainer = KeycloakContainer("quay.io/keycloak/keycloak:latest")
            .withRealmImportFile("/realm.json")
            .withAccessToHost(true)
            .withExposedPorts(8080)
            .withReuse(true)

        keycloakContainer.start()

        return ImmutableMap.of(
            "quarkus.oidc.enabled", "true",
            "quarkus.oidc.auth-server-url", keycloakContainer.authServerUrl.plus(contextPath),
            "quarkus.oidc.client-id", "quarkus-app",
            "quarkus.keycloak.admin-client.grant-type", OAuth2Constants.CLIENT_CREDENTIALS,
            "quarkus.oidc.tls.verification", "none",
            "quarkus.oidc.application-type", "service",
            "quarkus.oidc.token.issuer", keycloakContainer.authServerUrl.plus(contextPath),
        )
    }

    override fun inject(testInjector: TestInjector) {
        /*
         * We don't want to use the keycloak client given by the
         * keycloak container, so we build a new keycloak client.
         */

        val keycloakConfig = KeycloakConfig(
            serverUrl = keycloakContainer.authServerUrl,
            secret = "secret",
            clientId = "quarkus-app",
            realm = "codex",
            grantType = OAuth2Constants.PASSWORD
        )
        testInjector.injectIntoFields(
            keycloakConfig,
            AnnotatedAndMatchesType(Inject::class.java, KeycloakConfig::class.java)
        )
    }

    override fun stop() {
        if (::keycloakContainer.isInitialized && keycloakContainer.isRunning) {
            logger.debug("Stopping Keycloak test lifecycle...")
            keycloakContainer.stop()
        }
    }
}