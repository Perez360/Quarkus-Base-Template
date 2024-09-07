package com.codex.business.components.user.repo

import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import jakarta.persistence.PostUpdate
import jakarta.persistence.PrePersist
import jakarta.persistence.PreRemove
import jakarta.persistence.PreUpdate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserEntityListener {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostPersist
    fun postPersist(user: User) {
        logger.info("[UserEntityListener]: User has been persisted: $user")
    }

    @PrePersist
    fun prePersist(user: User) {
        logger.info("[UserEntityListener]: User is about to be persisted: $user")
    }

    @PostUpdate
    fun postUpdated(user: User) {
        logger.info("[UserEntityListener]: User has been updated: $user")
    }

    @PreUpdate
    fun preUpdatePersist(user: User) {
        logger.info("[UserEntityListener]: User is about to be updated: $user")
    }

    @PostLoad
    fun postLoad(user: User) {
        logger.info("[UserEntityListener]: User has been loaded: $user")
    }

    @PostRemove
    fun postRemove(user: User) {
        logger.info("[UserEntityListener]: User has been deleted: $user")
    }

    @PreRemove
    fun preRemove(user: User) {
        logger.info("[UserEntityListener]: User is about to be deleted: $user")
    }
}