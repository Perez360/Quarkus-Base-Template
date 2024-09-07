package com.codex.business.components.contact.dto

import com.codex.business.components.contact.enum.ContactType
import jakarta.validation.constraints.NotBlank

class AddContactDto {
    @NotBlank(message = "Contact content field must be provided")
    var content: String? = null

    var type: ContactType = ContactType.NONE

    @NotBlank(message = "Contact owner field must be provided")
    var userId: String? = null

    override fun toString(): String {
        return "AddContactDto(" +
                "content=$content, " +
                "type=$type, " +
                "userId=$userId" +
                ")"
    }
}