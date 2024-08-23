//package com.codex.business.components.user.boundary.http
//
//import com.codex.base.CODE_SUCCESS
//import com.codex.base.SYSTEM_CODE_SUCCESS
//import com.codex.base.shared.APIResponse
//import com.codex.base.shared.PagedContent
//import com.codex.base.utils.Mapper
//import com.codex.base.utils.keycloak.AccessTokenProvider
//import com.codex.base.utils.toPagedContent
//import com.codex.business.components.user.dto.UserDTO
//import com.codex.business.components.user.repo.User
//import com.codex.business.components.user.service.UserService
//import com.codex.business.components.user.spec.UserSpec
//import com.codex.business.mockAddUserDTO
//import com.codex.business.mockedUpdateUser
//import com.codex.business.mockedUser
//import io.mockk.every
//import io.mockk.junit5.MockKExtension
//import io.mockk.mockk
//import io.mockk.verify
//import io.quarkiverse.test.junit.mockk.InjectMock
//import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
//import io.quarkus.panache.common.Page
//import io.quarkus.test.common.http.TestHTTPEndpoint
//import io.quarkus.test.junit.QuarkusTest
//import io.restassured.RestAssured
//import io.restassured.http.ContentType
//import io.vertx.core.json.Json
//import jakarta.ws.rs.core.Response
//import org.hamcrest.Matchers
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//
//
//@QuarkusTest
//@ExtendWith(MockKExtension::class)
//@TestHTTPEndpoint(value = UserResourceImpl::class)
//class UserResourceImplTestNew : AccessTokenProvider() {
//
//    @InjectMock
//    private lateinit var userService: UserService
//
//
//    @Test
//    fun addUser() {
//        //GIVEN
//        val user = mockedUser()
//        val dto = mockAddUserDTO()
//        every { userService.add(any()) } returns user
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .body(Json.encode(dto))
//            .contentType(ContentType.JSON)
//            .`when`()
//            .post()
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        verify { userService.add(any()) }
//    }
//
//    @Test
//    fun updateUser() {
//        //GIVEN
//        val user = mockedUser()
//        val dto = mockedUpdateUser()
//        every { userService.getById(any()) } returns user
//        every { userService.update(any()) } returns user
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .body(Json.encode(dto))
//            .contentType(ContentType.JSON)
//            .`when`()
//            .put()
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        verify { userService.update(any()) }
//    }
//
//    @Test
//    fun getByUserId() {
//        //GIVEN
//        val user = mockedUser()
//        every { userService.getById(any()) } returns user
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .pathParam("id", user.id)
//            .`when`()
//            .get("/{id}")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        verify { userService.getById(any()) }
//    }
//
//    @Test
//    fun listAllUsers() {
//        //GIVEN
//        val page = 1
//        val size = 50
//        val panacheQuery = mockk<PanacheQuery<User>>()
//        every { panacheQuery.pageCount() } returns 50
//        every { panacheQuery.count() } returns 50
//        every { panacheQuery.page() } returns Page.of(page, size)
//        every { userService.list(any(), any()) } returns panacheQuery
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .queryParam("page", page)
//            .queryParam("size", size)
//            .`when`()
//            .get("/list")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<PagedContent<UserDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<PagedContent<UserDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<PagedContent<UserDTO>>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        verify { userService.list(any(), any()) }
//    }
//
//    @Test
//    fun searchUsers() {
//        //GIVEN
//        val userSpec = UserSpec()
//        userSpec.firstName = "Isaac"
//        val panacheQuery = mockk<PanacheQuery<User>>()
//        val pagedContent = PagedContent<UserDTO>()
//
//        every { panacheQuery.pageCount() } returns 10
//        every { panacheQuery.count() } returns 50
//        every { panacheQuery.page() } returns Page.of(userSpec.page!!, userSpec.size!!)
//        every { panacheQuery.toPagedContent<User, UserDTO>(Mapper::convert) } returns pagedContent
//        every { userService.search(any()) } returns panacheQuery
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .queryParam("firstName", userSpec.firstName)
//            .`when`()
//            .get("/q")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<PagedContent<UserDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<PagedContent<UserDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<PagedContent<UserDTO>>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log().all()
//
//        //THEN
//        verify { userService.search(userSpec) }
//
//    }
//
//    @Test
//    fun deleteUser() {
//        //GIVEN
//        val user = mockedUser()
//        every { userService.delete(any()) } returns user
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .pathParam("id", user.id)
//            .`when`()
//            .delete("/{id}")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<UserDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<UserDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        verify { userService.delete(any()) }
//    }
//}
//
