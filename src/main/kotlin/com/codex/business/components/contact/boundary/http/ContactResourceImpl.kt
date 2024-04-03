package com.codex.business.components.contact.boundary.http


import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.*
import com.codex.business.components.contact.boundary.ContactResource
import com.codex.business.components.contact.dto.AddContactDTO
import com.codex.business.components.contact.dto.ContactDTO
import com.codex.business.components.contact.dto.UpdateContactDTO
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.service.ContactService
import com.codex.business.components.contact.spec.ContactSpec
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Path("api/v1/contacts")
@Tag(name = "Contacts", description = "Manages everything related to contacts")
@Produces(MediaType.APPLICATION_JSON)
class ContactResourceImpl : ContactResource {

    @Inject
    private lateinit var contactService: ContactService

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    override fun addContact(dto: AddContactDTO): APIResponse<ContactDTO> {
        logger.info("Add contact route has been triggered with payload: {}", dto)
        val sessionId = Generator.generateSessionId()
        val oneContact = contactService.add(dto)
        val oneContactDTO = Mapper.convert<Contact, ContactDTO>(oneContact)
        logger.info("{}: Successfully created contact: {}", sessionId, oneContactDTO)
        return wrapSuccessInResponse(oneContactDTO)
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    override fun updateContact(dto: UpdateContactDTO): APIResponse<ContactDTO> {
        logger.info("Update contact route has been triggered with payload: {}", dto)
        val sessionId = Generator.generateSessionId()
        val oneContact = contactService.update(dto)
        val oneContactDTO = Mapper.convert<Contact, ContactDTO>(oneContact)
        logger.info("{}: Successfully updated contact: {}", sessionId, oneContactDTO)
        return wrapSuccessInResponse(oneContactDTO)
    }

    @Path("/{id}")
    @GET
    override fun getByContactId(@PathParam("id") id: String): APIResponse<ContactDTO> {
        logger.info("Get contact by id route has been triggered with id: {}", id)
        val sessionId = Generator.generateSessionId()
        val oneContact = contactService.getById(id)
        val oneContactDTO = Mapper.convert<Contact, ContactDTO>(oneContact)
        logger.info("{}: Found a contact: {}", sessionId, oneContactDTO)
        return wrapSuccessInResponse(oneContactDTO)
    }

    @GET
    @Path("/list")
    override fun listAllContacts(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int
    ): APIResponse<PagedContent<ContactDTO>> {
        logger.info("List contacts route has been triggered with page: {} and size: {}", page, size)
        val sessionId = Generator.generateSessionId()
        val pagedContacts: PagedContent<ContactDTO> = contactService.list(page, size)
            .toPagedContent(Mapper::convert)
        logger.info("{}: Contacts to list in pages: {}", sessionId, pagedContacts)
        return wrapSuccessInResponse(pagedContacts)
    }

    @GET
    @Path("/q")
    override fun searchContacts(@BeanParam contactSpec: ContactSpec): APIResponse<PagedContent<ContactDTO>> {
        logger.info("Search contacts route has been triggered with spec: {}", contactSpec)
        val sessionId = Generator.generateSessionId()
        val pagedContacts: PagedContent<ContactDTO> = contactService.search(contactSpec)
            .toPagedContent(Mapper::convert)
        logger.info("{}: Contacts to list in pages: {}", sessionId, pagedContacts)
        return wrapSuccessInResponse(pagedContacts)
    }

    @DELETE
    @Path("/{id}")
    override fun deleteContact(@PathParam("id") id: String): APIResponse<ContactDTO> {
        logger.info("Delete contact route has been triggered with id: {}", id)
        val sessionId = Generator.generateSessionId()
        val oneContact = contactService.delete(id)
        return if (oneContact != null) {
            val oneContactDTO = Mapper.convert<Contact, ContactDTO>(oneContact)
            logger.info("{}: Successfully deleted contact: {}", sessionId, oneContactDTO)
            wrapSuccessInResponse(oneContactDTO)
        } else {
            logger.info("{}: Failed to delete contact with id: $id", sessionId)
            wrapFailureInResponse("Failed to delete contact with id: $id")
        }
    }
}