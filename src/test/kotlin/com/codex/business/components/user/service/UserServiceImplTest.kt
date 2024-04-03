package com.codex.business.components.user.service

import com.codex.base.utils.search
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.repo.UserRepo
import com.codex.business.components.user.spec.UserSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.panache.common.Sort
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockMakers
import org.mockito.MockSettings
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@QuarkusTest
@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    @InjectMock
    private lateinit var userRepo: UserRepo

    @Inject
    private lateinit var underTest: UserService

    private lateinit var mockSettings: MockSettings

    @BeforeAll
    fun setUp() {
        mockSettings = Mockito.withSettings().mockMaker(MockMakers.INLINE)
    }

    @AfterAll
    fun tearDown() {
        underTest.deleteAll()
    }

    @Test
    fun add() {
        //GIVEN
        val mockedAddUserDTO: AddUserDTO = Mockito.mock(mockSettings)
        val mockedUser: User = Mockito.mock(mockSettings)

        //WHEN
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)
        val oneUser = underTest.add(mockedAddUserDTO)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Assertions.assertNotNull(oneUser)
        Assertions.assertInstanceOf(User::class.java, oneUser)
    }

    @Test
    fun update() {
        //GIVEN
        val mockedUpdateUserDTO: UpdateUserDTO = Mockito.mock()
        val mockedUser: User = Mockito.mock()

        //WHEN
        Mockito.`when`(userRepo.findById(mockedUpdateUserDTO.id!!)).thenReturn(mockedUser)
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        val oneUser = underTest.update(mockedUpdateUserDTO)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUpdateUserDTO.id!!)

        Assertions.assertNotNull(oneUser)
        Assertions.assertInstanceOf(User::class.java, oneUser)
        Assertions.assertSame(oneUser, mockedUser)
    }

    @Test
    fun getById() {
        //GIVEN
        val mockedUser: User = Mockito.mock(mockSettings)
        val userId = UUID.randomUUID().toString()
        Mockito.`when`(userRepo.findById(userId)).thenReturn(mockedUser)

        //WHEN
        val oneUser = underTest.getById(userId)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(userId)

        Assertions.assertNotNull(oneUser)
        Assertions.assertInstanceOf(User::class.java, oneUser)
        Assertions.assertSame(mockedUser, oneUser)
        Assertions.assertEquals(mockedUser.id, oneUser.id)
    }

    @Test
    fun list() {
        // Arrange
        val page = 1
        val size = 10

        val mockQuery: PanacheQuery<User> = Mockito.mock()
        Mockito.`when`(userRepo.findAll(Mockito.any(Sort::class.java))).thenReturn(mockQuery)

        // Act
        val result = underTest.list(page, size)

        // Assert
        assertEquals(mockQuery, result)
        Mockito.verify(mockQuery).page(page, size)
    }


    @Test
    fun search() {
        //GIVEN
        val userSpec = UserSpec()
        userSpec.page = 0
        userSpec.size = 50
        userSpec.sortBy = User::createdAt.name

        val panacheQuery: PanacheQuery<User> = Mockito.mock(mockSettings)

        println("Page and size: ${userSpec.page} ${userSpec.size}")
        println("Panache query $panacheQuery")
        //WHEN
        Mockito.`when`(panacheQuery.page(userSpec.page!!, userSpec.size!!)).thenReturn(panacheQuery)
        Mockito.`when`(userRepo.findAll(Sort.ascending(userSpec.sortBy))).thenReturn(panacheQuery)
        Mockito.`when`(userRepo.search(userSpec)).thenReturn(panacheQuery)
        val lists = underTest.search(userSpec)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).search(userSpec)
        Assertions.assertNotNull(lists)
    }

    @Test
    fun delete() {
        //GIVEN
        val mockedUser: User = Mockito.mock(mockSettings)

        //WHEN
        Mockito.`when`(userRepo.deleteById(mockedUser.id!!)).thenReturn(true)
        Mockito.`when`(userRepo.findById(mockedUser.id!!)).thenReturn(mockedUser)
        val deletedUser = underTest.delete(mockedUser.id!!)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteById(mockedUser.id!!)
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUser.id!!)
        Assertions.assertNotNull(deletedUser)
        Assertions.assertSame(deletedUser, mockedUser)
    }

//    @Test
//    fun it_should_throw_an_exception_when_delete() {
//        //GIVEN
//        val mockedUser = mockedUser()
//        val exception = ServiceException("Failed to delete user")
//
//        //WHEN
//        Mockito.`when`(userRepo.findById(mockedUser.id!!)).thenReturn(null)
//        Mockito.doThrow(exception).`when`(userRepo).findById(mockedUser.id!!)
//        Mockito.`when`(userRepo.deleteById(mockedUser.id!!)).thenReturn(true)
//        val deletedUser = underTest.delete(mockedUser.id!!)
//
//        //THEN
//        Mockito.verify(userRepo, Mockito.never()).deleteById(mockedUser.id!!)
//        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUser.id!!)
//        Assertions.assertThrows(exception::class.java) { }
//    }

    @Test
    fun it_should_delete_all() {
        //GIVEN
        val count = 10L

        //WHEN
        Mockito.`when`(userRepo.deleteAll()).thenReturn(count)
        val isDeleted = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteAll()
        Assertions.assertTrue(isDeleted)
    }

    @Test
    fun it_should_fail_to_delete_all() {
        //GIVEN
        val count = -1L

        //WHEN
        Mockito.`when`(userRepo.deleteAll()).thenReturn(count)
        val isDeleted = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteAll()
        Assertions.assertFalse(isDeleted)
    }
}