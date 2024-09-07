package com.codex.business

import com.codex.business.components.contact.dto.AddContactDto
import com.codex.business.components.contact.dto.ContactDto
import com.codex.business.components.contact.dto.UpdateContactDto
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.dto.AddUserDto
import com.codex.business.components.user.dto.UpdateUserDto
import com.codex.business.components.user.dto.UserDto
import com.codex.business.components.user.repo.User
import jakarta.persistence.Version
import jakarta.validation.constraints.PastOrPresent
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import java.lang.reflect.Field
import java.nio.charset.StandardCharsets
import java.time.LocalDate

private val parameter = EasyRandomParameters()
    .charset(StandardCharsets.UTF_8)
    .collectionSizeRange(1, 5)
    .randomizationDepth(100)
    .stringLengthRange(5, 32)
    .excludeField(FieldPredicates.isAnnotatedWith(Version::class.java))
    .scanClasspathForConcreteTypes(true)
    .ignoreRandomizationErrors(true)
    .randomize(FieldPredicates.isAnnotatedWith(PastOrPresent::class.java)) { LocalDate.now().minusYears(10) }
    .excludeField { field: Field -> field.type.name.startsWith("org.hibernate") }

private val easyRandom = EasyRandom(parameter)

/********************** CONTACT ***********************/
fun mockedContact(): Contact = easyRandom.nextObject(Contact::class.java)
fun mockAddContactDto(): AddContactDto = easyRandom.nextObject(AddContactDto::class.java)
fun mockedUpdateContactDto(): UpdateContactDto = easyRandom.nextObject(UpdateContactDto::class.java)
fun mockedContactDto(): ContactDto = easyRandom.nextObject(ContactDto::class.java)

/********************** USER ***********************/
fun mockedUser(): User = easyRandom.nextObject(User::class.java)
fun mockAddUserDto(): AddUserDto = easyRandom.nextObject(AddUserDto::class.java)
fun mockedUpdateUserDto(): UpdateUserDto = easyRandom.nextObject(UpdateUserDto::class.java)
fun mockedUserDto(): UserDto = easyRandom.nextObject(UserDto::class.java)
