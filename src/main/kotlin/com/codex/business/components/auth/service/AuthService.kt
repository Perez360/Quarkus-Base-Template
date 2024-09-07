package com.codex.business.components.auth.service

import com.codex.business.components.auth.dto.AddUserDto
import com.codex.business.components.auth.dto.GetUserTokenDto
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation

interface AuthService {
    fun getUserToken(dto: GetUserTokenDto): AccessTokenResponse
    fun listRoles(): List<RoleRepresentation>
    fun registerUser(dto: AddUserDto): UserRepresentation
}