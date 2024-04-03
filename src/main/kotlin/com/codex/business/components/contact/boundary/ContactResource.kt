package com.codex.business.components.contact.boundary


import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.ContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.spec.ContactSpec
import jakarta.validation.Valid


interface ContactResource {

    /**
     * Add a contact
     * @param dto AddContactDTO
     * @return  APIResponse<ContactDTO>
     */
    fun addContact(@Valid dto: AddContactDTO): APIResponse<ContactDTO>

    /**
     * Update a contact
     * @param dto UpdateContactDTO
     * @return  APIResponse<ContactDTO>
     */
    fun updateContact(@Valid dto: UpdateContactDTO): APIResponse<ContactDTO>

    /**
     * Get contact by id
     * @param id String
     * @return  APIResponse<ContactDTO>
     */
    fun getByContactId(id: String): APIResponse<ContactDTO>

    /**
     * List contacts
     * @param page
     * @param size
     * @return  APIResponse<PagedContent<ContactDTO>>
     */
    fun listAllContacts(page: Int, size: Int): APIResponse<PagedContent<ContactDTO>>

    /**
     * Search contacts
     * @param contactSpec: ContactSpec
     * @return APIResponse<PagedContent<ContactDTO>>
     */
    fun searchContacts(contactSpec: ContactSpec): APIResponse<PagedContent<ContactDTO>>

    /**
     * Delete a contact
     * @param id String
     * @return APIResponse<ContactDTO>
     */
    fun deleteContact(id: String): APIResponse<ContactDTO>
}
