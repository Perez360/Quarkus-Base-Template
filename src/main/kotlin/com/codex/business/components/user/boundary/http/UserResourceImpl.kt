package com.codex.business.components.user.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.Mapper
import com.codex.base.utils.toPagedContent
import com.codex.base.utils.wrapSuccessInResponse
import com.codex.business.components.user.boundary.streams.UserProducer
import com.codex.business.components.user.dto.AddUserDto
import com.codex.business.components.user.dto.UpdateUserDto
import com.codex.business.components.user.dto.UserDto
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.components.user.spec.UserSpec
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory


//@Authenticated
@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
//@SecurityScheme(securitySchemeName = "keycloak")
@Tag(name = "Users", description = "Manages everything related to users")
@ApplicationScoped
class UserResourceImpl : UserResource {

    @Inject
    private lateinit var userService: UserService

    @Inject
    private lateinit var userProducer: UserProducer

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    @POST
    //@RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun addUser(dto: AddUserDto): APIResponse<UserDto> {
        logger.info("Add user route has been triggered with dto: {}", dto)
        val oneUser = userService.add(dto)
        val oneUserDto = Mapper.convert<User, UserDto>(oneUser)
        userProducer.publish(oneUserDto)
        logger.info("Successfully created user: {}", oneUserDto)
        return wrapSuccessInResponse(oneUserDto)
    }

    @PUT
    //@RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    override fun updateUser(dto: UpdateUserDto): APIResponse<UserDto> {
        logger.info("Update user route has been triggered with dto: {}", dto)
        val oneUser = userService.update(dto)
        val oneUserDto = Mapper.convert<User, UserDto>(oneUser)
        logger.info("Successfully updated user: {}", oneUserDto)
        return wrapSuccessInResponse(oneUserDto)
    }


    @GET
    @Path("/{id}")
    //@RolesAllowed("ADMIN", "USER")
    override fun getByUserId(@PathParam("id") id: String): APIResponse<UserDto> {
        logger.info("Get user by id route has been triggered with id: {}", id)
        val oneUser = userService.getById(id)
        val oneUserDto = Mapper.convert<User, UserDto>(oneUser)
        logger.info("Found a user: {}", oneUserDto)
        return wrapSuccessInResponse(oneUserDto)
    }

    @GET
    @Path("/list")
    //@RolesAllowed("ADMIN", "USER")
    override fun listAllUsers(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int
    ): APIResponse<PagedContent<UserDto>> {
        logger.info("List users route has been triggered with page: {} and size: {}", page, size)
        val pagedUsers: PagedContent<UserDto> = userService.list(page, size)
            .toPagedContent(Mapper::convert)
        logger.info("Listed users in pages: {}", pagedUsers)
        return wrapSuccessInResponse(pagedUsers)
    }

    @GET
    @Path("/q")
    //@RolesAllowed("ADMIN", "USER")
    override fun searchUsers(@BeanParam userSpec: UserSpec): APIResponse<PagedContent<UserDto>> {
        logger.info("Search users route has been triggered with spec: {}", userSpec)
        val pagedUsers: PagedContent<UserDto> = userService.search(userSpec)
            .toPagedContent(Mapper::convert)
        logger.info("Searched users in pages: {}", pagedUsers)
        return wrapSuccessInResponse(pagedUsers)
    }

    @DELETE
    @Path("/{id}")
    //@RolesAllowed("ADMIN")
    override fun deleteUser(@PathParam("id") id: String): APIResponse<UserDto> {
        logger.info("Delete user route has been triggered with id: {}", id)
        val oneUser = userService.delete(id)
        val oneUserDto = Mapper.convert<User, UserDto>(oneUser)
        logger.info("Successfully deleted user: {}", oneUserDto)
        return wrapSuccessInResponse(oneUserDto)
    }
}