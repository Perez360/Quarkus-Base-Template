package com.codex.business.components.auth.boundary

//import com.codex.base.shared.APIResponse
//import com.codex.business.components.auth.dto.AddUserDTO
//import com.codex.business.components.auth.dto.GetUserTokenDTO
//import jakarta.validation.Valid
//import org.keycloak.representations.AccessTokenResponse
//import org.keycloak.representations.idm.RoleRepresentation
//import org.keycloak.representations.idm.UserRepresentation
//import java.security.Principal
//
//interface AuthResource {
//    fun me(): APIResponse<Principal>
//    fun getClientToken(): APIResponse<AccessTokenResponse>
//    fun getUserToken(@Valid dto: GetUserTokenDTO): APIResponse<AccessTokenResponse>
//    fun listRoles(): APIResponse<List<RoleRepresentation>>
//    fun registerUser(@Valid dto: AddUserDTO): APIResponse<UserRepresentation>
//}