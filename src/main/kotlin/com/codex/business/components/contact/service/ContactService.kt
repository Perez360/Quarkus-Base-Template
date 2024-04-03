package com.codex.business.components.contact.service

import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.spec.ContactSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import jakarta.validation.Valid

interface ContactService {
    fun add(@Valid dto: AddContactDTO): Contact

    fun update(@Valid dto: UpdateContactDTO): Contact

    fun getById(id: String): Contact

    fun list(page: Int, size: Int): PanacheQuery<Contact>

    fun search(userSpec: ContactSpec): PanacheQuery<Contact>

    fun delete(id: String): Contact?

    fun deleteAll(): Boolean
}