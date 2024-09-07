package com.codex.business.components.contact.boundary.http;

import com.codex.base.Texts.CODE_SUCCESS
import com.codex.base.Texts.SYSTEM_CODE_SUCCESS
import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.contact.dto.ContactDto
import com.codex.business.components.contact.enum.ContactType
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.service.ContactService
import com.codex.business.components.contact.spec.ContactSpec
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.mockAddContactDto
import com.codex.business.mockAddUserDto
import com.codex.business.mockedUpdateContactDto
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.vertx.core.json.Json
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@QuarkusTest
@TestHTTPEndpoint(value = ContactResourceImpl::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactResourceImplTestIT {

    private lateinit var testUser: User
    private lateinit var testContact: Contact

    @Inject
    private lateinit var contactService: ContactService

    @Inject
    private lateinit var userService: UserService

    @BeforeAll
    fun setUp() {
        testUser = userService.add(mockAddUserDto())
        testContact = contactService.add(mockAddContactDto().apply { userId = testUser.id })
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addContact() {
        //GIVEN
        val dto = mockAddContactDto().apply { userId = testUser.id }

        //WHEN
        RestAssured.given()
            .body(Json.encode(dto))
            .contentType(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<ContactDto>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<ContactDto>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<ContactDto>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateContact() {
        //GIVEN
        val dto = mockedUpdateContactDto()
        dto.id = testContact.id

        //WHEN
        RestAssured.given()
            .body(Json.encode(dto))
            .contentType(ContentType.JSON)
            .`when`()
            .put()
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<ContactDto>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<ContactDto>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<ContactDto>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun getByContactId() {
        RestAssured.given()
            .pathParam("id", testContact.id)
            .`when`()
            .get("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<ContactDto>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<ContactDto>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<ContactDto>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun listAllContacts() {
        //GIVEN
        val page = 1
        val size = 50

        //WHEN
        RestAssured.given()
            .queryParam("page", page)
            .queryParam("size", size)
            .`when`()
            .get("/list")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<PagedContent<ContactDto>>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<PagedContent<ContactDto>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<PagedContent<ContactDto>>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun searchContacts() {

        //GIVEN
        val contactSpec = ContactSpec()
        contactSpec.type = ContactType.MOBILE

        //WHEN
        RestAssured.given()
            .queryParam("type", contactSpec.type)
            .`when`()
            .get("/q")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<PagedContent<ContactDto>>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<PagedContent<ContactDto>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<PagedContent<ContactDto>>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteContact() {
        //GIVEN

        //WHEN
        RestAssured.given()
            .body(Json.encode(mockAddContactDto()))
            .pathParam("id", testContact.id)
            .`when`()
            .delete("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<ContactDto>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<ContactDto>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<ContactDto>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()
    }
}
