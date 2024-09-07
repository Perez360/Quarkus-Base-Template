package com.codex.business.components.auth.boundary.http

import com.codex.base.shared.APIResponse
import com.codex.business.components.auth.dto.AddUserDto
import com.codex.business.components.auth.dto.GetUserTokenDto
import com.codex.business.components.auth.dto.RoleDto
import jakarta.validation.Valid
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation

interface AuthResource {
    fun me(): APIResponse<String>
    fun getUserToken(@Valid dto: GetUserTokenDto): APIResponse<AccessTokenResponse>
    fun listRoles(): APIResponse<List<RoleDto>>
    fun registerUser(@Valid dto: AddUserDto): APIResponse<UserRepresentation>
}