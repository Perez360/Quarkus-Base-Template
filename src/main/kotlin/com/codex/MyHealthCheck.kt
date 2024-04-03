package com.codex

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness


@Liveness
@ApplicationScoped
class MyHealthCheck : HealthCheck {
    override fun call(): HealthCheckResponse {
        return HealthCheckResponse.up("I'm alive")
    }
}