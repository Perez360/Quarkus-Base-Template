package com.codex.business.components.user.boundary.http

import com.codex.base.CODE_FAILURE
import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_FAILURE
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.exceptions.ServiceException
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockAddUserDTO
import com.codex.business.mockedUpdateUser
import com.codex.business.mockedUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Page
import io.quarkus.test.InjectMock
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDate


@QuarkusTest
@TestHTTPEndpoint(value = UserResourceImpl::class)
class UserResourceImplTest {

    @Inject
    private lateinit var userResource: UserResource

    @InjectMock
    private lateinit var userService: UserService


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addUser_OK() {
        //GIVEN
        val user = mockedUser()
        val dto = mockAddUserDTO()
        Mockito.`when`(userService.add(dto)).thenReturn(user)

        //WHEN
        val result = userResource.addUser(dto)

        //THEN
        Mockito.verify(userService).add(dto)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addUser_KO() {
        //GIVEN
        val dto = mockAddUserDTO()
        Mockito.`when`(userService.add(dto)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = userResource.addUser(dto)

            //THEN
            Mockito.verify(userService).add(dto)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateUser_OK() {
        //GIVEN
        val user = mockedUser()
        val dto = mockedUpdateUser()
        Mockito.`when`(userService.update(dto)).thenReturn(user)

        //WHEN
        val result = userResource.updateUser(dto)

        //THEN
        Mockito.verify(userService).update(dto)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateUser_KO() {
        //GIVEN
        val dto = mockedUpdateUser()
        Mockito.`when`(userService.update(dto)).thenThrow(ServiceException::class.java)


        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = userResource.updateUser(dto)

            //THEN
            Mockito.verify(userService).update(dto)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun getByUserId_OK() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.getById(user.id!!)).thenReturn(user)

        //WHEN
        val result = userResource.getByUserId(user.id!!)

        //THEN
        Mockito.verify(userService).getById(user.id!!)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun getByUserId_KO() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.getById(user.id!!)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = userResource.getByUserId(user.id!!)

            //THEN
            Mockito.verify(userService).getById(user.id!!)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun listAllUsers_with_some_data() {
        //GIVEN
        val page = 1
        val size = 50
        val users = listOf(mockedUser(), mockedUser())
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(users)
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(page, size))
        Mockito.`when`(userService.list(page, size)).thenReturn(panacheQuery)

        //WHEN
        val result = userResource.listAllUsers(page, size)

        //THEN
        Mockito.verify(userService).list(page, size)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertFalse(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, page)
        Assertions.assertEquals(result.data?.size, size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun listAllUsers_with_no_data() {
        //GIVEN
        val page = 1
        val size = 50
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(page, size))
        Mockito.`when`(userService.list(page, size)).thenReturn(panacheQuery)

        //WHEN
        val result = userResource.listAllUsers(page, size)

        //THEN
        Mockito.verify(userService).list(page, size)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertTrue(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, page)
        Assertions.assertEquals(result.data?.size, size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun searchUsers_with_some_data() {
        //GIVEN
        val spec = UserSpec()
        spec.firstName = "Isaac"
        val users = listOf(mockedUser(), mockedUser())
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(users)
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(spec.page!!, spec.size!!))
        Mockito.`when`(userService.search(spec)).thenReturn(panacheQuery)

        //WHEN
        val result = userResource.searchUsers(spec)


        //THEN
        Mockito.verify(userService).search(spec)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertFalse(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, spec.page)
        Assertions.assertEquals(result.data?.size, spec.size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun searchUsers_with_no_data() {
        //GIVEN
        val spec = UserSpec()
        spec.firstName = "Isaac"
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(spec.page!!, spec.size!!))
        Mockito.`when`(userService.search(spec)).thenReturn(panacheQuery)

        //WHEN
        val result = userResource.searchUsers(spec)


        //THEN
        Mockito.verify(userService).search(spec)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertTrue(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, spec.page)
        Assertions.assertEquals(result.data?.size, spec.size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteUser_OK() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.delete(user.id!!)).thenReturn(user)

        //WHEN
        val result = userResource.deleteUser(user.id!!)

        //THEN
        Mockito.verify(userService).delete(user.id!!)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteUser_KO() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userService.delete(user.id!!)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = userResource.deleteUser(user.id!!)

            //THEN
            Mockito.verify(userService).delete(user.id!!)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }
}

