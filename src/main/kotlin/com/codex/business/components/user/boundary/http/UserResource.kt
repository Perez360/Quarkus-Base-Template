package com.codex.business.components.user.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.base.shared.PagedContent
import com.codex.business.components.user.dto.AddUserDto
import com.codex.business.components.user.dto.UpdateUserDto
import com.codex.business.components.user.dto.UserDto
import com.codex.business.components.user.spec.UserSpec
import jakarta.validation.Valid

interface UserResource {
    /**
     * Add a user
     * @param dto AddUserDto
     * @return APIResponse<UserDto>
     */
    fun addUser(@Valid dto: AddUserDto): APIResponse<UserDto>

    /**
     * Update a user
     * @param dto UpdateUserDto
     * @return APIResponse<UserDto>
     */
    fun updateUser(@Valid dto: UpdateUserDto): APIResponse<UserDto>

    /**
     * Get user by id
     * @param id String
     * @return APIResponse<UserDto>
     */
    fun getByUserId(id: String): APIResponse<UserDto>

    /**
     * List users
     * @param page
     * @param size
     * @return APIResponse<PagedContent<UserDto>>
     */
    fun listAllUsers(page: Int, size: Int): APIResponse<PagedContent<UserDto>>

    /**
     * Search users
     * @param userSpec:UserSpec
     * @return APIResponse<PagedContent<UserDto>>
     */
    fun searchUsers(userSpec: UserSpec): APIResponse<PagedContent<UserDto>>

    /**
     * Delete a user
     * @param id String
     * @return APIResponse<UserDto>
     */
    fun deleteUser(id: String): APIResponse<UserDto>
}