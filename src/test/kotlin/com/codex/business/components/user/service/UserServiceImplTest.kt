package com.codex.business.components.user.service

import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.repo.UserRepo
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockedUpdateUser
import com.codex.business.mockedUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
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
    private lateinit var mockSettings:MockSettings


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
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        //WHEN
        val result = underTest.add(mockedAddUserDTO)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
    }

    @Test
    fun update() {
        //GIVEN
        val mockedUpdateUserDTO: UpdateUserDTO = mockedUpdateUser()
        val mockedUser: User = mockedUser()
        Mockito.`when`(userRepo.findById(mockedUpdateUserDTO.id!!)).thenReturn(mockedUser)
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        //WHEN
        val result = underTest.update(mockedUpdateUserDTO)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUpdateUserDTO.id!!)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
        Assertions.assertSame(result, mockedUser)
    }

    @Test
    fun getById() {
        //GIVEN
        val mockedUser: User = Mockito.mock(mockSettings)
        val userId = UUID.randomUUID().toString()
        Mockito.`when`(userRepo.findById(userId)).thenReturn(mockedUser)

        //WHEN
        val result = underTest.getById(userId)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(userId)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
        Assertions.assertSame(mockedUser, result)
        Assertions.assertEquals(mockedUser.id, result.id)
    }

    @Test
    fun list() {
        // GIVEN
        val page = 1
        val size = 10
        val panacheQuery: PanacheQuery<User> = Mockito.mock()
        Mockito.`when`(userRepo.findAll()).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.page(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)

        // WHEN
        val result = underTest.list(page, size)

        // THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findAll()
        Assertions.assertEquals(panacheQuery, result)
    }


    @Test
    fun search() {
        //GIVEN
        val userSpec = UserSpec()
        userSpec.firstName = "Kofi"
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(userRepo.search(userSpec)).thenReturn(panacheQuery)

        //WHEN
        val result = underTest.search(userSpec)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).search(userSpec)
        Assertions.assertNotNull(result)
    }

    @Test
    fun delete() {
        //GIVEN
        val mockedUser: User = mockedUser()
        Mockito.`when`(userRepo.deleteById(mockedUser.id!!)).thenReturn(true)
        Mockito.`when`(userRepo.findById(mockedUser.id!!)).thenReturn(mockedUser)

        //WHEN
        val result = underTest.delete(mockedUser.id!!)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteById(mockedUser.id!!)
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUser.id!!)
        Assertions.assertNotNull(result)
        Assertions.assertSame(result, mockedUser)
    }

    @Test
    fun it_should_delete_all() {
        //GIVEN
        val count = 10L
        Mockito.`when`(userRepo.deleteAll()).thenReturn(count)

        //WHEN
        val result = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteAll()
        Assertions.assertTrue(result)
    }

    @Test
    fun it_should_fail_to_delete_all() {
        //GIVEN
        val count = -1L
        Mockito.`when`(userRepo.deleteAll()).thenReturn(count)

        //WHEN
        val result = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteAll()
        Assertions.assertFalse(result)
    }
}