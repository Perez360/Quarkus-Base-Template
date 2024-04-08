package com.codex.business.components.user.service

import com.codex.business.mockedUpdateUser
import com.codex.business.mockedUser
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.repo.UserRepo
import com.codex.business.components.user.spec.UserSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.*
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
        val mockedAddUserDTO: AddUserDTO = Mockito.mock(Mockito.withSettings().mockMaker(MockMakers.INLINE))
        val mockedUser: User = Mockito.mock(Mockito.withSettings().mockMaker(MockMakers.INLINE))

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
        val mockedUpdateUserDTO: UpdateUserDTO = mockedUpdateUser()
        val mockedUser: User = mockedUser()

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
        // GIVEN
        val page = 1
        val size = 10
        val mockedPanacheQuery: PanacheQuery<User> = Mockito.mock(mockSettings)
        val mockedPanacheRepositoryBase1: PanacheRepositoryBase<User, String> = Mockito.mock(mockSettings)
        Mockito.`when`(userRepo.findAll())
            .thenReturn(mockedPanacheQuery)
        Mockito.`when`(mockedPanacheRepositoryBase1.findAll())
            .thenReturn(mockedPanacheQuery)
        // GIVEN
        val panacheQuery = underTest.list(page, size)

        // WHEN
        Assertions.assertEquals(mockedPanacheQuery, panacheQuery)
        Mockito.verify(userRepo).findAll()
    }


    @Test
    fun search() {
        //GIVEN
        val userSpec: UserSpec = Mockito.mock(mockSettings)

        val mockedPanacheQuery: PanacheQuery<User> = Mockito.mock()
        Mockito.`when`(userRepo.search(userSpec)).thenReturn(mockedPanacheQuery)

        //WHEN
        val panacheQuery = underTest.search(userSpec)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).search(userSpec)
        Assertions.assertNotNull(panacheQuery)
    }

    @Test
    fun delete() {
        //GIVEN
        val mockedUser: User = mockedUser()
        println("Id: ${mockedUser.id}")

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