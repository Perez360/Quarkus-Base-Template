package com.codex.business.integration.components.client

import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("")
@RegisterRestClient
interface TestClient {

}