package com.codex.business.components.contact.service

import com.codex.business.components.contact.repo.ContactRepo
import com.codex.business.components.user.repo.UserRepo
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@QuarkusTest
class ContactServiceImplTest {

    @InjectMock
    private lateinit var contactRepo: ContactRepo

    @InjectMock
    private lateinit var userRepo: UserRepo

    @Inject
    private lateinit var underTest: ContactService


    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun add() {
        //GIVEN
    }

    @Test
    fun update() {
    }

    @Test
    fun getById() {
    }

    @Test
    fun list() {
    }

    @Test
    fun search() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun deleteAll() {
    }
}