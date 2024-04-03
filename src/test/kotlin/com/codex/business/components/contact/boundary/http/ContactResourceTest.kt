package com.codex.business.components.contact.boundary.http

import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.SYSTEM_MESSAGE_SUCCESS
import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.mockAddContactDTO
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class ContactResourceTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun addContact() {
        val url = "/api/v1/contacts"
        val dto: AddContactDTO = mockAddContactDTO()

        RestAssured.given()
            .`when`()
            .body(dto)
            .contentType(MediaType.APPLICATION_JSON)
            .post(url)
            .then()
            .statusCode(Response.Status.OK.statusCode)
            .body("code", Matchers.`is`(CODE_SUCCESS))
            .body("message", Matchers.`is`("Success"))
            .body("systemCode", Matchers.`is`(SYSTEM_CODE_SUCCESS))
            .body("systemMessage", Matchers.`is`(SYSTEM_MESSAGE_SUCCESS))
            .body("data", Matchers.notNullValue())
    }

    @Test
    fun updateContact() {
    }

    @Test
    fun getByContactId() {
    }

    @Test
    fun listAllContacts() {
    }

    @Test
    fun searchContacts() {
    }

    @Test
    fun deleteContact() {
    }
}