package com.codex.business.integrations.notifications.service

import com.arjuna.ats.jdbc.TransactionalDriver
import com.codex.base.exceptions.ServiceException
import com.codex.business.integrations.notifications.dto.MailRequestDto
import com.codex.business.integrations.notifications.dto.MailResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets

@ApplicationScoped
class MailService {

    @ConfigProperty(name = "email.server.url")
    private lateinit var url: String

    @ConfigProperty(name = "email.server.port")
    private lateinit var port: String

    @ConfigProperty(name = "email.server.username")
    private lateinit var username: String

    @ConfigProperty(name = "email.server.password")
    private lateinit var password: String

    @Inject
    private lateinit var mapper: ObjectMapper

    @Inject
    private lateinit var httpClientBuilder: HttpClient.Builder

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    fun sendEmail(dto: MailRequestDto): MailResponseDto {

        val request = HttpRequest.newBuilder(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(dto.toJsonString(), StandardCharsets.UTF_8))
            .build()

        val client = httpClientBuilder.authenticator(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, TransactionalDriver.password.toCharArray())
            }
        }).build()

        try {

            val response = client.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8))

            logger.info("Response received from mail server: {}", response)

            return mapper.readValue(response.body(), MailResponseDto::class.java)

        } catch (io: Exception) {
            throw ServiceException("Failed to send email")

        } catch (ie: InterruptedException) {
            throw ServiceException("Failed to send email")
        }
    }
}