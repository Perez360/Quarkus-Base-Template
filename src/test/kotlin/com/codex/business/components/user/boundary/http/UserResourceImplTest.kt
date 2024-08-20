package com.codex.business.components.user.boundary.http

import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.Mapper
import com.codex.base.utils.keycloak.AccessTokenProvider
import com.codex.base.utils.toPagedContent
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockAddUserDTO
import com.codex.business.mockedUpdateUser
import com.codex.business.mockedUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.test.InjectMock
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.vertx.core.json.Json
import jakarta.ws.rs.core.Response
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockMakers
import org.mockito.MockSettings
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension


@QuarkusTest
@ExtendWith(MockitoExtension::class)
@TestHTTPEndpoint(value = UserResourceImpl::class)
class UserResourceImplTest : AccessTokenProvider() {

    @InjectMock
    lateinit var userService: UserService

    @Test
    fun addUser() {
        //GIVEN
        val user = mockedUser()
        val dto = mockAddUserDTO()
        Mockito.`when`(userService.add(dto)).thenReturn(user)
        val adminToken = getAccessToken("isaac360", "PEREZ360")


        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
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
            .log()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).add(dto)

    }

    @Test
    fun updateUser() {
        //GIVEN
        val user = mockedUser()
        val dto = mockedUpdateUser()
        Mockito.`when`(userService.update(dto)).thenReturn(user)
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
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
            .log()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).update(dto)
    }

    @Test
    fun getByUserId() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.getById(Mockito.anyString())).thenReturn(user)
        val adminToken = getAccessToken("isaac360", "PEREZ360")


        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
            .pathParam("id", user.id)
            .`when`()
            .get("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).getById(Mockito.anyString())
    }

    @Test
    fun listAllUsers() {
        //GIVEN
        val page = 1
        val size = 50
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(userService.list(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.page()).thenReturn(Mockito.mock())
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
            .queryParam("page", page)
            .queryParam("size", size)
            .`when`()
            .get("/list")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).list(Mockito.anyInt(), Mockito.anyInt())
    }

    @Test
    fun searchUsers() {
        //GIVEN
        val userSpec = UserSpec()
        userSpec.firstName = "Isaac"
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        val pagedContent = PagedContent<UserDTO>()
        Mockito.`when`(panacheQuery.page()).thenReturn(Mockito.mock())
        Mockito.`when`(panacheQuery.toPagedContent<User, UserDTO>(Mapper::convert)).thenReturn(pagedContent)
        Mockito.`when`(userService.search(userSpec)).thenReturn(panacheQuery)
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
            .queryParam("firstName", userSpec.firstName)
            .`when`()
            .get("/q")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log().all()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).search(userSpec)

    }

    @Test
    fun deleteUser() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.delete(user.id!!)).thenReturn(user)
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
            .body(Json.encode(mockAddUserDTO()))
            .pathParam("id", user.id)
            .`when`()
            .delete("/{id}")
            .then()
            .body(Matchers.notNullValue())
            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
            .statusCode(Response.Status.OK.statusCode)
            .log()

        //THEN
        Mockito.verify(userService, Mockito.atLeastOnce()).delete(user.id!!)
    }
}

