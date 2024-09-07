package com.codex.business.components.contact.repo

import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import jakarta.persistence.PostUpdate
import jakarta.persistence.PrePersist
import jakarta.persistence.PreRemove
import jakarta.persistence.PreUpdate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ContactEntityListener {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostPersist
    fun postPersist(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact has been persisted: $contact")
    }

    @PrePersist
    fun prePersist(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact is about to be persisted: $contact")
    }

    @PostUpdate
    fun postUpdated(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact has been updated: $contact")
    }

    @PreUpdate
    fun preUpdatePersist(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact is about to be updated: $contact")
    }

    @PostLoad
    fun postLoad(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact has been loaded: $contact")
    }

    @PostRemove
    fun postRemove(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact has been deleted: $contact")
    }

    @PreRemove
    fun preRemove(contact: Contact) {
        logger.info("[ContactEntityListener]: Contact is about to be deleted: $contact")
    }
}