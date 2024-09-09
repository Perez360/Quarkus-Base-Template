package com.codex.business.integrations.components.clients

import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("")
@RegisterRestClient(configKey = "")
interface TestClient