package com.codex.base.utils

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.inject.Singleton
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.net.http.HttpClient
import java.time.Duration
import java.util.concurrent.Executors
import javax.net.ssl.SSLContext

@ApplicationScoped
class CustomBeans {

    @ConfigProperty(name = "system.http-connection.timeout")
    private lateinit var connectionTimeOut: String

    @Produces
    @Singleton
    fun producesHttpClientWithTrustCertificates(): HttpClient.Builder {
        return HttpClient.newBuilder()
            .executor(Executors.newWorkStealingPool())
            .version(HttpClient.Version.HTTP_2)
            .sslContext(SSLContext.getInstance("SSL"))
            .connectTimeout(Duration.ofMillis(connectionTimeOut.toLong()))
    }
}