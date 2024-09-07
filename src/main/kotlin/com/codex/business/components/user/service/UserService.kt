package com.codex.business.components.user.service

import com.codex.business.components.user.dto.AddUserDto
import com.codex.business.components.user.dto.UpdateUserDto
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.spec.UserSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery

interface UserService {
    fun add(dto: AddUserDto): User

    fun update(dto: UpdateUserDto): User

    fun getById(id: String): User

    fun list(page: Int, size: Int): PanacheQuery<User>

    fun search(userSpec: UserSpec): PanacheQuery<User>

    fun delete(id: String): User

    fun deleteAll(): Long
}