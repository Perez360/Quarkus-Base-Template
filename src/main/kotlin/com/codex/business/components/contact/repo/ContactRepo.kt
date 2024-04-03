package com.codex.business.components.contact.repo

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ContactRepo : PanacheRepositoryBase<Contact, String>