package com.codex.business.components.user.service

import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.spec.UserSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery

interface UserService {
    fun add(dto: AddUserDTO): User

    fun update(dto: UpdateUserDTO): User

    fun getById(id: String): User

    fun list(page: Int, size: Int): PanacheQuery<User>

    fun search(userSpec: UserSpec): PanacheQuery<User>

    fun delete(id: String): User

    fun deleteAll(): Boolean
}