package com.codex.business.components.user.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.dto.UserDTO
import com.codex.business.components.user.spec.UserSpec
import jakarta.validation.Valid

interface UserResource {
    /**
     * Add a user
     * @param dto AddUserDTO
     * @return APIResponse<UserDTO>
     */
    fun addUser(@Valid dto: AddUserDTO): APIResponse<UserDTO>

    /**
     * Update a user
     * @param dto UpdateUserDTO
     * @return APIResponse<UserDTO>
     */
    fun updateUser(@Valid dto: UpdateUserDTO): APIResponse<UserDTO>

    /**
     * Get user by id
     * @param id String
     * @return APIResponse<UserDTO>
     */
    fun getByUserId(id: String): APIResponse<UserDTO>

    /**
     * List users
     * @param page
     * @param size
     * @return APIResponse<PagedContent<UserDTO>>
     */
    fun listAllUsers(page: Int, size: Int): APIResponse<PagedContent<UserDTO>>

    /**
     * Search users
     * @param userSpec:UserSpec
     * @return APIResponse<PagedContent<UserDTO>>
     */
    fun searchUsers(userSpec: UserSpec): APIResponse<PagedContent<UserDTO>>

    /**
     * Delete a user
     * @param id String
     * @return APIResponse<UserDTO>
     */
    fun deleteUser(id: String): APIResponse<UserDTO>
}