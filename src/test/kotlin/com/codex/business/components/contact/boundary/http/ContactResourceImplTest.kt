//package com.codex.business.components.contact.boundary.http
//
//import com.codex.base.CODE_SUCCESS
//import com.codex.base.SYSTEM_CODE_SUCCESS
//import com.codex.base.shared.APIResponse
//import com.codex.base.shared.PagedContent
//import com.codex.base.utils.Mapper
//import com.codex.base.utils.keycloak.AccessTokenProvider
//import com.codex.base.utils.toPagedContent
//import com.codex.business.components.contact.dto.ContactDTO
//import com.codex.business.components.contact.repo.Contact
//import com.codex.business.components.contact.service.ContactService
//import com.codex.business.components.contact.spec.ContactSpec
//import com.codex.business.components.user.repo.UserRepo
//import com.codex.business.mockAddContactDTO
//import com.codex.business.mockedContact
//import com.codex.business.mockedUpdateContactDTO
//import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
//import io.quarkus.panache.common.Page
//import io.quarkus.test.InjectMock
//import io.quarkus.test.common.http.TestHTTPEndpoint
//import io.quarkus.test.junit.QuarkusTest
//import io.restassured.RestAssured
//import io.restassured.http.ContentType
//import io.vertx.core.json.Json
//import jakarta.ws.rs.core.Response
//import org.hamcrest.Matchers
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.MockMakers
//import org.mockito.MockSettings
//import org.mockito.Mockito
//import org.mockito.junit.jupiter.MockitoExtension
//
//
//@QuarkusTest
//@ExtendWith(MockitoExtension::class)
//@TestHTTPEndpoint(value = ContactResourceImpl::class)
//class ContactResourceImplTest : AccessTokenProvider() {
//
//    @InjectMock
//    lateinit var contactService: ContactService
//
//    @InjectMock
//    lateinit var userRepo: UserRepo
//
//    private lateinit var mockSettings: MockSettings
//
//    @BeforeEach
//    fun setUp() {
//        mockSettings = Mockito.withSettings().mockMaker(MockMakers.INLINE)
//    }
//
//    @Test
//    fun addContact() {
//        //GIVEN
//        val contact = mockedContact()
//        val dto = mockAddContactDTO()
//        Mockito.`when`(userRepo.findById(Mockito.anyString())).thenReturn(contact.user)
//        Mockito.`when`(contactService.add(dto)).thenReturn(contact)
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
//            .body(APIResponse<ContactDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atLeastOnce()).add(dto)
//
//    }
//
//    @Test
//    fun updateContact() {
//        //GIVEN
//        val contact = mockedContact()
//        val dto = mockedUpdateContactDTO()
//        Mockito.`when`(contactService.update(dto)).thenReturn(contact)
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
//            .body(APIResponse<ContactDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atLeastOnce()).update(dto)
//    }
//
//    @Test
//    fun getByContactId() {
//        //GIVEN
//        val contact = mockedContact()
//        Mockito.`when`(contactService.getById(Mockito.anyString())).thenReturn(contact)
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .pathParam("id", contact.id)
//            .`when`()
//            .get("/{id}")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<ContactDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atLeastOnce()).getById(Mockito.anyString())
//    }
//
//    @Test
//    fun listAllContacts() {
//        //GIVEN
//        val page = 1
//        val size = 50
//        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
//        Mockito.`when`(contactService.list(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)
//        Mockito.`when`(panacheQuery.page()).thenReturn(Mockito.mock())
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
//            .body(APIResponse<PagedContent<ContactDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<PagedContent<ContactDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<PagedContent<ContactDTO>>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atLeastOnce()).list(Mockito.anyInt(), Mockito.anyInt())
//    }
//
//    @Test
//    fun searchContacts() {
//        //GIVEN
//        val spec = ContactSpec()
//        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
//        val pagedContent = Mockito.mock<PagedContent<ContactDTO>>(mockSettings)
//        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(0, 50))
//        Mockito.`when`(panacheQuery.list()).thenReturn(listOf(mockedContact()))
//        Mockito.`when`(panacheQuery.toPagedContent<Contact, ContactDTO>(Mapper::convert)).thenReturn(pagedContent)
//        Mockito.`when`(panacheQuery.hasPreviousPage()).thenReturn(Mockito.anyBoolean())
//        Mockito.`when`(panacheQuery.hasNextPage()).thenReturn(Mockito.anyBoolean())
//        Mockito.`when`(panacheQuery.pageCount()).thenReturn(Mockito.anyInt())
//        Mockito.`when`(contactService.search(spec)).thenReturn(panacheQuery)
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .queryParam("content", spec.content)
//            .`when`()
//            .get("/q")
//            .then()
////            .body(Matchers.notNullValue())
////            .body(APIResponse<PagedContent<ContactDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
////            .body(APIResponse<PagedContent<ContactDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
////            .body(APIResponse<PagedContent<ContactDTO>>::data.name, Matchers.notNullValue())
////            .statusCode(Response.Status.OK.statusCode)
//            .log().all()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atMostOnce()).search(spec)
//    }
//
//    @Test
//    fun searchContactsNew() {
//        // GIVEN
//        val spec = ContactSpec().apply { content = "0249065507" }
//        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
//        val pagedContent = Mockito.mock<PagedContent<ContactDTO>>(mockSettings)
//
//        // Ensure the mocked methods return the correct types
//        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(0, 50))
//        Mockito.`when`(panacheQuery.list()).thenReturn(listOf(mockedContact()))
//        Mockito.`when`(panacheQuery.toPagedContent<Contact, ContactDTO>(Mapper::convert)).thenReturn(pagedContent)
//        Mockito.`when`(contactService.search(spec)).thenReturn(panacheQuery)
//
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        // WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .queryParam("content", spec.content)
//            .`when`()
//            .get("/q")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<PagedContent<ContactDTO>>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<PagedContent<ContactDTO>>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<PagedContent<ContactDTO>>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log().all()
//
//        // THEN
//        Mockito.verify(contactService, Mockito.atMostOnce()).search(spec)
//    }
//
//
//    @Test
//    fun deleteContact() {
//        //GIVEN
//        val contact = mockedContact()
//        Mockito.`when`(contactService.delete(Mockito.anyString())).thenReturn(contact)
//        val adminToken = getAccessToken("isaac360", "PEREZ360")
//
//        //WHEN
//        RestAssured.given().auth().oauth2(adminToken)
//            .body(Json.encode(mockAddContactDTO()))
//            .pathParam("id", contact.id)
//            .`when`()
//            .delete("/{id}")
//            .then()
//            .body(Matchers.notNullValue())
//            .body(APIResponse<ContactDTO>::code.name, Matchers.equalTo(CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::systemCode.name, Matchers.equalTo(SYSTEM_CODE_SUCCESS))
//            .body(APIResponse<ContactDTO>::data.name, Matchers.notNullValue())
//            .statusCode(Response.Status.OK.statusCode)
//            .log()
//
//        //THEN
//        Mockito.verify(contactService, Mockito.atLeastOnce()).delete(Mockito.anyString())
//    }
//}
//
