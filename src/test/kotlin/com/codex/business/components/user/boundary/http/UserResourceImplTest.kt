package com.codex.business.components.user.boundary.http

import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.keycloak.AccessTokenProvider
import com.codex.base.utils.wrapSuccessInResponse
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockAddUserDTO
import com.codex.business.mockedUpdateUser
import com.codex.business.mockedUserDTO
import io.quarkus.test.InjectMock
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.vertx.core.json.Json
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockMakers
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension


@QuarkusTest
@ExtendWith(MockitoExtension::class)
@TestHTTPEndpoint(value = UserResourceImpl::class)
class UserResourceImplTest : AccessTokenProvider() {

    @InjectMock
    private lateinit var userResource: UserResource

    @Test
    fun addUser() {
        //GIVEN
        val user = mockedUserDTO()
        val dto = mockAddUserDTO()
        Mockito.`when`(userResource.addUser(Mockito.any())).thenReturn(wrapSuccessInResponse(user))
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
            .log().all()

        //THEN
        Mockito.verify(userResource, Mockito.atMostOnce()).addUser(Mockito.any())

    }

    @Test
    fun updateUser() {
        //GIVEN
        val user = mockedUserDTO()
        val dto = mockedUpdateUser()
        Mockito.`when`(userResource.updateUser(dto)).thenReturn(wrapSuccessInResponse(user))
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
            .log().all()

        //THEN
        Mockito.verify(userResource, Mockito.atLeastOnce()).updateUser(dto)
    }

    @Test
    fun getByUserId() {
        //GIVEN
        val user = mockedUserDTO()
        Mockito.`when`(userResource.getByUserId(Mockito.anyString())).thenReturn(wrapSuccessInResponse(user))
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
            .log().all()

        //THEN
        Mockito.verify(userResource, Mockito.atLeastOnce()).getByUserId(Mockito.anyString())
    }

    @Test
    fun listAllUsers() {
        //GIVEN
        val page = 1
        val size = 50
        val pagedContent: PagedContent<UserDTO> = Mockito.mock(Mockito.withSettings().mockMaker(MockMakers.INLINE))
        Mockito.`when`(userResource.listAllUsers(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(wrapSuccessInResponse(pagedContent))
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
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

        //THEN
        Mockito.verify(userResource, Mockito.atLeastOnce()).listAllUsers(Mockito.anyInt(), Mockito.anyInt())
    }

    @Test
    fun searchUsers() {
        //GIVEN
        val userSpec = UserSpec()
        userSpec.firstName = "Isaac"
        val pagedContent: PagedContent<UserDTO> = Mockito.mock(Mockito.withSettings().mockMaker(MockMakers.INLINE))
        Mockito.`when`(userResource.searchUsers(userSpec)).thenReturn(wrapSuccessInResponse(pagedContent))
        val adminToken = getAccessToken("isaac360", "PEREZ360")

        //WHEN
        RestAssured.given().auth().oauth2(adminToken)
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

        //THEN
        Mockito.verify(userResource, Mockito.atLeastOnce()).searchUsers(userSpec)

    }

    @Test
    fun deleteUser() {
        //GIVEN
        val user = mockedUserDTO()
        Mockito.`when`(userResource.deleteUser(user.id!!)).thenReturn(wrapSuccessInResponse(user))
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
            .log().all()

        //THEN
        Mockito.verify(userResource, Mockito.atLeastOnce()).deleteUser(user.id!!)
    }
}

