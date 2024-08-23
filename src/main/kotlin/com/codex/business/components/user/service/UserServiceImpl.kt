package com.codex.business.components.user.service

import com.codex.base.exceptions.ServiceException
import com.codex.business.components.user.dto.AddUserDTO
import com.codex.business.components.user.dto.UpdateUserDTO
import com.codex.business.components.user.repo.User
import com.codex.business.components.user.repo.UserRepo
import com.codex.business.components.user.spec.UserSpec
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
@Transactional
class UserServiceImpl : UserService {

    @Inject
    private lateinit var userRepo: UserRepo

    override fun add(dto: AddUserDTO): User {
        val oneUser = User()
        oneUser.firstName = dto.firstName
        oneUser.lastName = dto.lastName
        oneUser.dateOfBirth = dto.dateOfBirth
        oneUser.status = dto.status
        oneUser.role = dto.role

        userRepo.persist(oneUser)
        return oneUser
    }

    override fun update(dto: UpdateUserDTO): User {
        val oneUser = getById(dto.id!!)

        oneUser.firstName = dto.firstName
        oneUser.lastName = dto.lastName
        oneUser.dateOfBirth = dto.dateOfBirth
        oneUser.role = dto.role
        oneUser.status = dto.status

        userRepo.persist(oneUser)
        return oneUser
    }

    override fun getById(id: String): User {
        val oneUser = userRepo.findById(id)
            ?: throw ServiceException("No user found with id: $id")
        return oneUser
    }

    override fun list(page: Int, size: Int): PanacheQuery<User> {
        val panacheQuery: PanacheQuery<User> = userRepo.findAll()
            .page(page, size)
        return panacheQuery
    }

    override fun search(userSpec: UserSpec): PanacheQuery<User> {
        val panacheQuery = userRepo.search(userSpec)
        return panacheQuery
    }

    override fun delete(id: String): User {
        val oneUser = getById(id)
        val isDeleted = userRepo.deleteById(id)
        if (!isDeleted) throw ServiceException("Failed to delete user")
        return oneUser
    }

    override fun deleteAll(): Boolean = userRepo.deleteAll() >= 0
}