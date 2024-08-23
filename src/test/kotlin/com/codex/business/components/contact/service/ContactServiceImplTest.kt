//package com.codex.business.components.contact.service
//
//import com.codex.business.components.contact.dto.UpdateContactDTO
//import com.codex.business.components.contact.repo.Contact
//import com.codex.business.components.contact.repo.ContactRepo
//import com.codex.business.components.contact.spec.ContactSpec
//import com.codex.business.components.user.repo.UserRepo
//import com.codex.business.mockAddContactDTO
//import com.codex.business.mockedContact
//import com.codex.business.mockedUpdateContactDTO
//import com.codex.business.mockedUser
//import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
//import io.quarkus.test.InjectMock
//import io.quarkus.test.junit.QuarkusTest
//import jakarta.inject.Inject
//import org.junit.jupiter.api.*
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.MockMakers
//import org.mockito.MockSettings
//import org.mockito.Mockito
//import org.mockito.junit.jupiter.MockitoExtension
//import java.util.*
//
//
//@QuarkusTest
//@ExtendWith(MockitoExtension::class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class ContactServiceImplTest {
//    @InjectMock
//    private lateinit var contactRepo: ContactRepo
//
//    @InjectMock
//    private lateinit var userRepo: UserRepo
//
//    @Inject
//    private lateinit var underTest: ContactService
//
//    private lateinit var mockSettings: MockSettings
//
//    @BeforeAll
//    fun setUp() {
//        mockSettings = Mockito.withSettings().mockMaker(MockMakers.INLINE)
//    }
//
//    @AfterAll
//    fun tearDown() {
//        underTest.deleteAll()
//    }
//
//    @Test
//    fun add() {
//        //GIVEN
//        val mockedAddContactDTO = mockAddContactDTO()
//        val mockedContact = mockedContact()
//        val mockedUser = mockedUser()
//        Mockito.`when`(userRepo.findById(Mockito.anyString())).thenReturn(mockedUser)
//        Mockito.doNothing().`when`(contactRepo).persist(mockedContact)
//
//        //WHEN
//        val result = underTest.add(mockedAddContactDTO)
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).persist(mockedContact)
//        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(Mockito.anyString())
//        Assertions.assertNotNull(result)
//        Assertions.assertInstanceOf(Contact::class.java, result)
//    }
//
//    @Test
//    fun update() {
//        //GIVEN
//        val mockedUpdateContactDTO: UpdateContactDTO = mockedUpdateContactDTO()
//        val mockedContact: Contact = mockedContact()
//        Mockito.`when`(contactRepo.findById(mockedUpdateContactDTO.id!!)).thenReturn(mockedContact)
//        Mockito.doNothing().`when`(contactRepo).persist(mockedContact)
//
//        //WHEN
//        val result = underTest.update(mockedUpdateContactDTO)
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).persist(mockedContact)
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).findById(mockedUpdateContactDTO.id!!)
//        Assertions.assertNotNull(result)
//        Assertions.assertInstanceOf(Contact::class.java, result)
//        Assertions.assertSame(result, mockedContact)
//    }
//
//    @Test
//    fun getById() {
//        //GIVEN
//        val mockedContact: Contact = Mockito.mock(mockSettings)
//        val userId = UUID.randomUUID().toString()
//        Mockito.`when`(contactRepo.findById(userId)).thenReturn(mockedContact)
//
//        //WHEN
//        val result = underTest.getById(userId)
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).findById(userId)
//        Assertions.assertNotNull(result)
//        Assertions.assertInstanceOf(Contact::class.java, result)
//        Assertions.assertSame(mockedContact, result)
//        Assertions.assertEquals(mockedContact.id, result.id)
//    }
//
//    @Test
//    fun list() {
//        // GIVEN
//        val page = 1
//        val size = 10
//        val panacheQuery: PanacheQuery<Contact> = Mockito.mock()
//        Mockito.`when`(contactRepo.findAll()).thenReturn(panacheQuery)
//        Mockito.`when`(panacheQuery.page(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)
//
//        // WHEN
//        val result = underTest.list(page, size)
//
//        // THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).findAll()
//        Assertions.assertEquals(panacheQuery, result)
//    }
//
//
//    @Test
//    fun search() {
//        //GIVEN
//        val spec = ContactSpec()
//        spec.content = "0249065507"
//        val panacheQuery = Mockito.mock<PanacheQuery<Contact>>()
//        Mockito.`when`(contactRepo.search(spec)).thenReturn(panacheQuery)
//
//        //WHEN
//        val result = underTest.search(spec)
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).search(spec)
//        Assertions.assertNotNull(result)
//    }
//
//    @Test
//    fun delete() {
//        //GIVEN
//        val mockedContact: Contact = mockedContact()
//        Mockito.`when`(contactRepo.deleteById(mockedContact.id!!)).thenReturn(true)
//        Mockito.`when`(contactRepo.findById(mockedContact.id!!)).thenReturn(mockedContact)
//
//        //WHEN
//        val result = underTest.delete(mockedContact.id!!)
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).deleteById(mockedContact.id!!)
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).findById(mockedContact.id!!)
//        Assertions.assertNotNull(result)
//        Assertions.assertSame(result, mockedContact)
//    }
//
//    @Test
//    fun it_should_delete_all() {
//        //GIVEN
//        val count = 10L
//        Mockito.`when`(contactRepo.deleteAll()).thenReturn(count)
//
//        //WHEN
//        val result = underTest.deleteAll()
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).deleteAll()
//        Assertions.assertTrue(result)
//    }
//
//    @Test
//    fun it_should_fail_to_delete_all() {
//        //GIVEN
//        val count = -1L
//        Mockito.`when`(contactRepo.deleteAll()).thenReturn(count)
//
//        //WHEN
//        val result = underTest.deleteAll()
//
//        //THEN
//        Mockito.verify(contactRepo, Mockito.atMostOnce()).deleteAll()
//        Assertions.assertFalse(result)
//    }
//}