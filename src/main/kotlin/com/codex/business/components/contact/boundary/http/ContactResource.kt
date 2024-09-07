package com.codex.business.components.contact.boundary.http


import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.contact.dto.AddContactDto
import com.codex.business.components.contact.dto.ContactDto
import com.codex.business.components.contact.dto.UpdateContactDto
import com.codex.business.components.contact.spec.ContactSpec
import jakarta.validation.Valid


interface ContactResource {

    /**
     * Add a contact
     * @param dto AddContactDto
     * @return  APIResponse<ContactDto>
     */
    fun addContact(@Valid dto: AddContactDto): APIResponse<ContactDto>

    /**
     * Update a contact
     * @param dto UpdateContactDto
     * @return  APIResponse<ContactDto>
     */
    fun updateContact(@Valid dto: UpdateContactDto): APIResponse<ContactDto>

    /**
     * Get contact by id
     * @param id String
     * @return  APIResponse<ContactDto>
     */
    fun getByContactId(id: String): APIResponse<ContactDto>

    /**
     * List contacts
     * @param page
     * @param size
     * @return  APIResponse<PagedContent<ContactDto>>
     */
    fun listAllContacts(page: Int, size: Int): APIResponse<PagedContent<ContactDto>>

    /**
     * Search contacts
     * @param contactSpec: ContactSpec
     * @return APIResponse<PagedContent<ContactDto>>
     */
    fun searchContacts(contactSpec: ContactSpec): APIResponse<PagedContent<ContactDto>>

    /**
     * Delete a contact
     * @param id String
     * @return APIResponse<ContactDto>
     */
    fun deleteContact(id: String): APIResponse<ContactDto>
}
