package com.codex.business.components.contact.boundary.http


import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.*
import com.codex.business.components.contact.dto.AddContactDto
import com.codex.business.components.contact.dto.ContactDto
import com.codex.business.components.contact.dto.UpdateContactDto
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.contact.service.ContactService
import com.codex.business.components.contact.spec.ContactSpec
import io.quarkus.security.Authenticated
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Authenticated
@Path("api/v1/contacts")
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "keycloak")
@Tag(name = "Contacts", description = "Manages everything related to contacts")
class ContactResourceImpl : ContactResource {

    @Inject
    private lateinit var contactService: ContactService

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @POST
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun addContact(dto: AddContactDto): APIResponse<ContactDto> {
        logger.info("Add contact route has been triggered with payload: {}", dto)
        val oneContact = contactService.add(dto)
        val oneContactDto = Mapper.convert<Contact, ContactDto>(oneContact)
        logger.info("Successfully created contact: {}", oneContactDto)
        return wrapSuccessInResponse(oneContactDto)
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun updateContact(dto: UpdateContactDto): APIResponse<ContactDto> {
        logger.info("Update contact route has been triggered with payload: {}", dto)
        val oneContact = contactService.update(dto)
        val oneContactDto = Mapper.convert<Contact, ContactDto>(oneContact)
        logger.info("Successfully updated contact: {}", oneContactDto)
        return wrapSuccessInResponse(oneContactDto)
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN", "USER")
    override fun getByContactId(@PathParam("id") id: String): APIResponse<ContactDto> {
        logger.info("Get contact by id route has been triggered with id: {}", id)
        val oneContact = contactService.getById(id)
        val oneContactDto = Mapper.convert<Contact, ContactDto>(oneContact)
        logger.info("Found a contact: {}", oneContactDto)
        return wrapSuccessInResponse(oneContactDto)
    }

    @GET
    @Path("/list")
    @RolesAllowed("ADMIN", "USER")
    override fun listAllContacts(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int
    ): APIResponse<PagedContent<ContactDto>> {
        logger.info("List contacts route has been triggered with page: {} and size: {}", page, size)
        val pagedContacts: PagedContent<ContactDto> = contactService.list(page, size)
            .toPagedContent(Mapper::convert)
        logger.info("Contacts to list in pages: {}", pagedContacts)
        return wrapSuccessInResponse(pagedContacts)
    }

    @GET
    @Path("/q")
    @RolesAllowed("ADMIN", "USER")
    override fun searchContacts(@BeanParam contactSpec: ContactSpec): APIResponse<PagedContent<ContactDto>> {
        logger.info("Search contacts route has been triggered with spec: {}", contactSpec)
        val pagedContacts: PagedContent<ContactDto> = contactService.search(contactSpec)
            .toPagedContent(Mapper::convert)
        logger.info("Contacts to search in pages: {}", pagedContacts)
        return wrapSuccessInResponse(pagedContacts)
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    override fun deleteContact(@PathParam("id") id: String): APIResponse<ContactDto> {
        logger.info("Delete contact route has been triggered with id: {}", id)
        val oneContact = contactService.delete(id)
        return if (oneContact != null) {
            val oneContactDto = Mapper.convert<Contact, ContactDto>(oneContact)
            logger.info("Successfully deleted contact: {}", oneContactDto)
            wrapSuccessInResponse(oneContactDto)
        } else {
            logger.info("Failed to delete contact with id: $id")
            wrapFailureInResponse("Failed to delete contact with id: $id")
        }
    }
}