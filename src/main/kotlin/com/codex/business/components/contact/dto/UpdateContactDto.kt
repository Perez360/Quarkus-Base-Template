package com.codex.business.components.contact.dto

import com.codex.business.components.contact.enum.ContactType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class UpdateContactDto {
    @NotBlank(message = "Id field must be provided")
    var id: String? = null

    @NotBlank(message = "User id content field must be provided")
    var userId: String? = null

    @NotBlank(message = "Content field must be provided")
    var content: String? = null

    @NotNull(message = "Type field must be provided")
    var type: ContactType? = null


    override fun toString(): String {
        return "UpdateContactDto(" +
                "id=$id, " +
                "useId=$userId, " +
                "content=$content, " +
                "type=$type" +
                ")"
    }
}