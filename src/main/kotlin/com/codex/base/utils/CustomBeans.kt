package com.codex.base.utils

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import java.net.http.HttpClient
import java.time.Duration
import java.util.concurrent.Executors
import javax.net.ssl.SSLContext

@ApplicationScoped
class CustomBeans {
    @Produces
    fun producesHttpClientWithTrustCertificates(): HttpClient.Builder {
        return HttpClient.newBuilder()
            .executor(Executors.newWorkStealingPool())
            .version(HttpClient.Version.HTTP_2)
            .sslContext(SSLContext.getInstance("SSL"))
            .connectTimeout(Duration.ofMinutes(1))


    }
}