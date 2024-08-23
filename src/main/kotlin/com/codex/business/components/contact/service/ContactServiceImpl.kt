package com.codex.business.components.contact.service

import com.codex.base.exceptions.ServiceException
import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.repo.ContactRepo
import com.codex.business.components.contact.spec.ContactSpec
import com.codex.business.components.user.repo.UserRepo
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
@Transactional
class ContactServiceImpl : ContactService {

    @Inject
    private lateinit var contactRepo: ContactRepo

    @Inject
    private lateinit var userRepo: UserRepo

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun add(dto: AddContactDTO): Contact {

        val oneUser = userRepo.findById(dto.userId!!)
            ?: throw ServiceException("No user found with id: ${dto.userId}")

        val oneContact = Contact()
        oneContact.content = dto.content
        oneContact.type = dto.type
        oneContact.user = oneUser

        contactRepo.persist(oneContact)
        return oneContact
    }

    override fun update(dto: UpdateContactDTO): Contact {

        val oneContact = getById(dto.id!!)
        val oneUser = userRepo.findById(dto.userId!!)

        oneContact.user = oneUser
        oneContact.content = dto.content
        oneContact.type = dto.type

        contactRepo.persist(oneContact)
        return oneContact
    }

    override fun getById(id: String): Contact {
        val oneContact = contactRepo.findById(id)
            ?: throw ServiceException("No contact found with id: $id")

        return oneContact
    }

    override fun list(page: Int, size: Int): PanacheQuery<Contact> {
        val panacheQuery: PanacheQuery<Contact> = contactRepo.findAll()
            .page(page, size)

        return panacheQuery
    }

    override fun search(userSpec: ContactSpec): PanacheQuery<Contact> {
        val panacheQuery = contactRepo.search(userSpec)

        return panacheQuery
    }

    override fun delete(id: String): Contact? {
        val oneContact = getById(id)
        val isDeleted = contactRepo.deleteById(id)
        return if (isDeleted) oneContact else null
    }

    override fun deleteAll(): Boolean {
        val deleteCount = contactRepo.deleteAll()

        return deleteCount >= 0
    }
}