package com.codex.business.components.user.service

import com.codex.base.exceptions.ServiceException
import com.codex.business.components.user.dto.UpdateUserDto
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.repo.UserRepo
import com.codex.business.components.user.spec.UserSpec
import com.codex.business.mockAddUserDto
import com.codex.business.mockedUpdateUserDto
import com.codex.business.mockedUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito


@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
    @InjectMock
    private lateinit var userRepo: UserRepo

    @Inject
    private lateinit var underTest: UserService

    @AfterAll
    fun tearDown() {
        underTest.deleteAll()
    }

    @Test
    fun add_OK() {
        //GIVEN
        val mockedAddUserDto = mockAddUserDto()
        val mockedUser: User = mockedUser()
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        //WHEN
        val result = underTest.add(mockedAddUserDto)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
    }


    @Test
    fun update_OK() {
        //GIVEN
        val mockedUpdateUserDto: UpdateUserDto = mockedUpdateUserDto()
        val mockedUser: User = mockedUser()
        Mockito.`when`(userRepo.findById(mockedUpdateUserDto.id!!)).thenReturn(mockedUser)
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        //WHEN
        val result = underTest.update(mockedUpdateUserDto)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).persist(mockedUser)
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUpdateUserDto.id!!)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
        Assertions.assertSame(result, mockedUser)
    }

    @Test
    fun update_KO() {
        //GIVEN
        val mockedUpdateUserDto: UpdateUserDto = mockedUpdateUserDto()
        val mockedUser: User = mockedUser()
        Mockito.`when`(userRepo.findById(mockedUpdateUserDto.id!!)).thenReturn(null)
        Mockito.doNothing().`when`(userRepo).persist(mockedUser)

        Assertions.assertThrows(ServiceException::class.java) {
            //WHEN
            underTest.update(mockedUpdateUserDto)

            //THEN
            Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUpdateUserDto.id!!)
            Mockito.verify(userRepo, Mockito.never()).persist(mockedUser)
        }
    }

    @Test
    fun getById_OK() {
        //GIVEN
        val mockedUser = mockedUser()
        Mockito.`when`(userRepo.findById(mockedUser.id!!)).thenReturn(mockedUser)

        //WHEN
        val result = underTest.getById(mockedUser.id!!)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findById(mockedUser.id!!)
        Assertions.assertNotNull(result)
        Assertions.assertInstanceOf(User::class.java, result)
        Assertions.assertSame(mockedUser, result)
        Assertions.assertEquals(mockedUser.id, result.id)
    }

    @Test
    fun getById_KO() {
        //GIVEN
        val user = mockedUser()
        Mockito.`when`(userRepo.findById(user.id!!)).thenReturn(null)

        Assertions.assertThrows(ServiceException::class.java) {
            //WHEN
            underTest.getById(user.id!!)

            //THEN
            Mockito.verify(userRepo, Mockito.atMostOnce()).findById(user.id!!)
        }
    }

    @Test
    fun list_with_some_data() {
        // GIVEN
        val page = 1
        val size = 10
        val users = listOf(mockedUser(), mockedUser())
        val panacheQuery: PanacheQuery<User> = Mockito.mock()
        Mockito.`when`(userRepo.findAll()).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.page(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.list()).thenReturn(users)

        // WHEN
        val result = underTest.list(page, size)

        // THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findAll()
        Assertions.assertEquals(panacheQuery, result)
        Assertions.assertFalse(result.list().isEmpty())
    }


    @Test
    fun list_with_no_data() {
        // GIVEN
        val page = 1
        val size = 10
        val panacheQuery: PanacheQuery<User> = Mockito.mock()
        Mockito.`when`(userRepo.findAll()).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.page(Mockito.anyInt(), Mockito.anyInt())).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())

        // WHEN
        val result = underTest.list(page, size)

        // THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).findAll()
        Assertions.assertEquals(panacheQuery, result)
        Assertions.assertTrue(panacheQuery.list().isEmpty())
    }


    @Test
    fun search_with_no_data() {
        //GIVEN
        val userSpec = UserSpec()
        userSpec.firstName = "Kofi"
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(userRepo.search(userSpec)).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.list()).thenReturn(emptyList())

        //WHEN
        val result = underTest.search(userSpec)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).search(userSpec)
        Assertions.assertNotNull(result)
        Assertions.assertTrue(result.list().isEmpty())
    }

    @Test
    fun search_with_some_data() {
        //GIVEN
        val users = listOf(mockedUser(), mockedUser())
        val userSpec = UserSpec()
        userSpec.firstName = "Kofi"
        val panacheQuery = Mockito.mock<PanacheQuery<User>>()
        Mockito.`when`(userRepo.search(userSpec)).thenReturn(panacheQuery)
        Mockito.`when`(panacheQuery.list()).thenReturn(users)

        //WHEN
        val result = underTest.search(userSpec)

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).search(userSpec)
        Assertions.assertFalse(result.list().isEmpty())

    }

    @Test
    fun delete_OK() {
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
    fun delete_KO() {
        //GIVEN
        val mockedUser = mockedUser()
        Mockito.`when`(userRepo.findById(mockedUser.id!!)).thenReturn(null)
        Mockito.`when`(userRepo.deleteById(mockedUser.id!!)).thenReturn(true)

        Assertions.assertThrows(ServiceException::class.java) {
            //WHEN
            underTest.delete(mockedUser.id!!)

            //THEN
            Mockito.verify(userRepo, Mockito.only()).findById(mockedUser.id!!)
            Mockito.verify(userRepo, Mockito.never()).deleteById(mockedUser.id!!)
        }
    }

    @Test
    fun deleteAll_OK() {
        //GIVEN
        Mockito.`when`(userRepo.deleteAll()).thenReturn(9)

        //WHEN
        val result = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.atMostOnce()).deleteAll()
        Assertions.assertTrue(result > 0)
    }

    @Test
    fun deleteAll_KO() {
        //GIVEN
        Mockito.`when`(userRepo.deleteAll()).thenReturn(0)

        //WHEN
        val result = underTest.deleteAll()

        //THEN
        Mockito.verify(userRepo, Mockito.only()).deleteAll()
        Assertions.assertTrue(result < 1)
    }
}