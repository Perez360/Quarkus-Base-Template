package com.codex.business.components.user.boundary.http

import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockAddUserDTO
import com.codex.business.mockedUpdateUser
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
import java.time.LocalDate

@QuarkusTest
@TestHTTPEndpoint(value = UserResourceImpl::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserResourceImplTestIT {

    private lateinit var testUser: User

    @Inject
    private lateinit var service: UserService

    @BeforeAll
    fun setUp() {
        testUser = service.add(mockAddUserDTO())
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addUser() {
        //GIVEN
        val dto = mockAddUserDTO()

        //WHEN
        RestAssured.given()
            .body(Json.encode(dto))
            .contentType(ContentType.JSON)
            .`when`()
            .post()
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateUser() {
        //GIVEN
        val dto = mockedUpdateUser()
        dto.id = testUser.id

        //WHEN
        RestAssured.given()
            .body(Json.encode(dto))
            .contentType(ContentType.JSON)
            .`when`()
            .put()
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun getByUserId() {
        RestAssured.given()
            .pathParam("id", testUser.id)
            .`when`()
            .get("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun listAllUsers() {
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
            .body(APIResponse<PagedContent<UserDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<PagedContent<UserDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<PagedContent<UserDTO>>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun searchUsers() {

        //GIVEN
        val userSpec = UserSpec()
        userSpec.firstName = "Isaac"

        //WHEN
        RestAssured.given()
            .queryParam("firstName", userSpec.firstName)
            .`when`()
            .get("/q")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<PagedContent<UserDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<PagedContent<UserDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<PagedContent<UserDTO>>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteUser() {
        //GIVEN

        //WHEN
        RestAssured.given()
            .body(Json.encode(mockAddUserDTO()))
            .pathParam("id", testUser.id)
            .`when`()
            .delete("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()
    }
}
