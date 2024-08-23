package com.codex.business.components.contact.boundary.http

import com.codex.base.CODE_FAILURE
import com.codex.base.CODE_SUCCESS
import com.codex.base.SYSTEM_CODE_FAILURE
import com.codex.base.SYSTEM_CODE_SUCCESS
import com.codex.base.exceptions.ServiceException
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.service.ContactService
import com.codex.business.components.contact.spec.ContactSpec
import com.codex.business.components.user.boundary.http.UserResourceImpl
import com.codex.business.mockAddContactDTO
import com.codex.business.mockedContact
import com.codex.business.mockedUpdateContactDTO
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


@QuarkusTest
@TestHTTPEndpoint(value = UserResourceImpl::class)
class ContactResourceImplTest {

    @Inject
    private lateinit var contactResource: ContactResource

    @InjectMock
    private lateinit var contactService: ContactService


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addUser_OK() {
        //GIVEN
        val user = mockedContact()
        val dto = mockAddContactDTO()
        Mockito.`when`(contactService.add(dto)).thenReturn(user)

        //WHEN
        val result = contactResource.addContact(dto)

        //THEN
        Mockito.verify(contactService).add(dto)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun addContact_KO() {
        //GIVEN
        val dto = mockAddContactDTO()
        Mockito.`when`(contactService.add(dto)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = contactResource.addContact(dto)

            //THEN
            Mockito.verify(contactService).add(dto)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateContact_OK() {
        //GIVEN
        val contact = mockedContact()
        val dto = mockedUpdateContactDTO()
        Mockito.`when`(contactService.update(dto)).thenReturn(contact)

        //WHEN
        val result = contactResource.updateContact(dto)

        //THEN
        Mockito.verify(contactService).update(dto)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun updateContact_KO() {
        //GIVEN
        val dto = mockedUpdateContactDTO()
        Mockito.`when`(contactService.update(dto)).thenThrow(ServiceException::class.java)


        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = contactResource.updateContact(dto)

            //THEN
            Mockito.verify(contactService).update(dto)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun getByContactId_OK() {
        //GIVEN
        val user = mockedContact()
        Mockito.`when`(contactService.getById(user.id!!)).thenReturn(user)

        //WHEN
        val result = contactResource.getByContactId(user.id!!)

        //THEN
        Mockito.verify(contactService).getById(user.id!!)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun getByContactId_KO() {
        //GIVEN
        val user = mockedContact()
        Mockito.`when`(contactService.getById(user.id!!)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = contactResource.getByContactId(user.id!!)

            //THEN
            Mockito.verify(contactService).getById(user.id!!)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun listAllContacts_with_some_data() {
        //GIVEN
        val page = 1
        val size = 50
        val users = listOf(mockedContact(), mockedContact())
        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(users)
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(page, size))
        Mockito.`when`(contactService.list(page, size)).thenReturn(panacheQuery)

        //WHEN
        val result = contactResource.listAllContacts(page, size)

        //THEN
        Mockito.verify(contactService).list(page, size)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertFalse(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, page)
        Assertions.assertEquals(result.data?.size, size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun listAllContacts_with_no_data() {
        //GIVEN
        val page = 1
        val size = 50
        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(page, size))
        Mockito.`when`(contactService.list(page, size)).thenReturn(panacheQuery)

        //WHEN
        val result = contactResource.listAllContacts(page, size)

        //THEN
        Mockito.verify(contactService).list(page, size)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertTrue(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, page)
        Assertions.assertEquals(result.data?.size, size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun searchContacts_with_some_data() {
        //GIVEN
        val spec = ContactSpec()
        spec.content = "0249065507"
        val contacts = listOf(mockedContact(), mockedContact())
        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(contacts)
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(spec.page!!, spec.size!!))
        Mockito.`when`(contactService.search(spec)).thenReturn(panacheQuery)

        //WHEN
        val result = contactResource.searchContacts(spec)


        //THEN
        Mockito.verify(contactService).search(spec)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertFalse(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, spec.page)
        Assertions.assertEquals(result.data?.size, spec.size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN", "USER"])
    fun searchContacts_with_no_data() {
        //GIVEN
        val spec = ContactSpec()
        spec.content = "0249065507"
        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())
        Mockito.`when`(panacheQuery.page()).thenReturn(Page.of(spec.page!!, spec.size!!))
        Mockito.`when`(contactService.search(spec)).thenReturn(panacheQuery)

        //WHEN
        val result = contactResource.searchContacts(spec)

        //THEN
        Mockito.verify(contactService).search(spec)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertTrue(result.data?.data.isNullOrEmpty())
        Assertions.assertEquals(result.data?.page, spec.page)
        Assertions.assertEquals(result.data?.size, spec.size)
    }

    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteContact_OK() {
        //GIVEN
        val contact = mockedContact()
        Mockito.`when`(contactService.delete(contact.id!!)).thenReturn(contact)

        //WHEN
        val result = contactResource.deleteContact(contact.id!!)

        //THEN
        Mockito.verify(contactService).delete(contact.id!!)
        Assertions.assertEquals(result.code, CODE_SUCCESS)
        Assertions.assertEquals(result.systemCode, SYSTEM_CODE_SUCCESS)
        Assertions.assertNotNull(result.data)
    }


    @Test
    @TestSecurity(user = "isaac360", roles = ["ADMIN"])
    fun deleteContact_KO() {
        //GIVEN
        val contact = mockedContact()
        Mockito.`when`(contactService.delete(contact.id!!)).thenThrow(ServiceException::class.java)

        Assertions.assertThrowsExactly(ServiceException::class.java) {
            //WHEN
            val result = contactResource.deleteContact(contact.id!!)

            //THEN
            Mockito.verify(contactService).delete(contact.id!!)
            Assertions.assertEquals(result.code, CODE_FAILURE)
            Assertions.assertEquals(result.systemCode, SYSTEM_CODE_FAILURE)
            Assertions.assertNull(result.data)
        }
    }
}

