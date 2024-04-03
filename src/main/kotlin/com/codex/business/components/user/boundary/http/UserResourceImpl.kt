package com.codex.business.components.user.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.base.utils.Generator
import com.codex.base.utils.Mapper
import com.codex.base.utils.toPagedContent
import com.codex.base.utils.wrapSuccessInResponse
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.service.UserService
import com.codex.business.components.user.spec.UserSpec
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Path("/api/v1/users")
@Tag(name = "Users", description = "Manages everything related to users")
@Produces(MediaType.APPLICATION_JSON)
class UserResourceImpl : UserResource {

    @Inject
    private lateinit var userService: UserService

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    override fun addUser(dto: AddUserDTO): APIResponse<UserDTO> {
        logger.info("Add user route has been triggered with dto: {}", dto)
        val sessionId = Generator.generateSessionId()
        val oneUser = userService.add(dto)
        val oneUserDTO = Mapper.convert<User, UserDTO>(oneUser)
        logger.info("{}: Successfully created user: {}", sessionId, oneUserDTO)
        return wrapSuccessInResponse(oneUserDTO)
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    override fun updateUser(dto: UpdateUserDTO): APIResponse<UserDTO> {
        val sessionId = Generator.generateSessionId()
        logger.info("Update user route has been triggered with dto: {}", dto)
        val oneUser = userService.update(dto)
        val oneUserDTO = Mapper.convert<User, UserDTO>(oneUser)
        logger.info("{}: Successfully updated user: {}", sessionId, oneUserDTO)
        return wrapSuccessInResponse(oneUserDTO)
    }


    @GET
    @Path("/{id}")
    override fun getByUserId(@PathParam("id") id: String): APIResponse<UserDTO> {
        logger.info("Get user by id route has been triggered with id: {}", id)
        val sessionId = Generator.generateSessionId()
        val oneUser = userService.getById(id)
        val oneUserDTO = Mapper.convert<User, UserDTO>(oneUser)
        logger.info("{}: Found a user: {}", sessionId, oneUserDTO)
        return wrapSuccessInResponse(oneUserDTO)
    }

    @GET
    @Path("/list")
    override fun listAllUsers(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int
    ): APIResponse<PagedContent<UserDTO>> {
        logger.info("List users route has been triggered with page: {} and size: {}", page, size)
        val sessionId = Generator.generateSessionId()
        val pagedUsers: PagedContent<UserDTO> = userService.list(page, size)
            .toPagedContent(Mapper::convert)
        logger.info("{}: Users to list in pages: {}", sessionId, pagedUsers)
        return wrapSuccessInResponse(pagedUsers)
    }

    @GET
    @Path("/q")
    override fun searchUsers(@BeanParam userSpec: UserSpec): APIResponse<PagedContent<UserDTO>> {
        logger.info("Search users route has been triggered with spec: {}", userSpec)
        val sessionId = Generator.generateSessionId()
        val pagedUsers: PagedContent<UserDTO> = userService.search(userSpec)
            .toPagedContent(Mapper::convert)
        logger.info("{}: Users to list in pages: {}", sessionId, pagedUsers)
        return wrapSuccessInResponse(pagedUsers)
    }

    @DELETE
    @Path("/{id}")
    override fun deleteUser(@PathParam("id") id: String): APIResponse<UserDTO> {
        logger.info("Delete user route has been triggered with id: {}", id)
        val sessionId = Generator.generateSessionId()
        val oneUser = userService.delete(id)
        val oneUserDTO = Mapper.convert<User, UserDTO>(oneUser)
        logger.info("{}: Successfully deleted user: {}", sessionId, oneUserDTO)
        return wrapSuccessInResponse(oneUserDTO)
    }
}