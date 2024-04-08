package com.codex.business.components.contact.repo

import com.codex.base.utils.CustomPanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ContactRepo : CustomPanacheRepositoryBase<Contact, String>