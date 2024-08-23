package com.codex.business

import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.ContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.repo.User
import jakarta.persistence.Version
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import java.lang.reflect.Field
import java.nio.charset.StandardCharsets

private val parameter = EasyRandomParameters()
    .charset(StandardCharsets.UTF_8)
    .collectionSizeRange(1, 5)
    .randomizationDepth(100)
    .stringLengthRange(5, 32)
    .excludeField(FieldPredicates.isAnnotatedWith(Version::class.java))
    .scanClasspathForConcreteTypes(true)
    .ignoreRandomizationErrors(true)
    .excludeField { field: Field -> field.type.name.startsWith("org.hibernate") }

private val easyRandom = EasyRandom(parameter)

/********************** CONTACT ***********************/
fun mockedContact(): Contact = easyRandom.nextObject(Contact::class.java)
fun mockAddContactDTO(): AddContactDTO = easyRandom.nextObject(AddContactDTO::class.java)
fun mockedUpdateContactDTO(): UpdateContactDTO = easyRandom.nextObject(UpdateContactDTO::class.java)
fun mockedContactDTO(): ContactDTO = easyRandom.nextObject(ContactDTO::class.java)

/********************** USER ***********************/
fun mockedUser(): User = easyRandom.nextObject(User::class.java)
fun mockAddUserDTO(): AddUserDTO = easyRandom.nextObject(AddUserDTO::class.java)
fun mockedUpdateUser(): UpdateUserDTO = easyRandom.nextObject(UpdateUserDTO::class.java)
fun mockedUserDTO(): UserDTO = easyRandom.nextObject(UserDTO::class.java)
