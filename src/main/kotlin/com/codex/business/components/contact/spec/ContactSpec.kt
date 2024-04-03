package com.codex.business.components.contact.spec

import com.codex.base.shared.Query
import com.codex.base.shared.Spec
import com.codex.business.components.contact.enum.ContactType
import io.quarkus.panache.common.Parameters
import jakarta.ws.rs.QueryParam

class ContactSpec : Spec() {
    @QueryParam(value = "content")
    var value: String? = null

    @QueryParam(value = "type")
    var type: ContactType? = null

    @QueryParam(value = "userId")
    var user: String? = null

    override fun toParameters(): Parameters {
        return Parameters.with(::value.name, value)
            .and(::value.name, value)
            .and(::type.name, type)
            .and(::user.name, user)
    }

    override fun queryDefinition(entry: Map.Entry<String, Any?>): String {
        return when (entry.key) {
            ::value.name -> Query.contains(entry.key, entry.key)
            ::user.name -> Query.isEqualTo(entry.key+".id", entry.key)
            else -> Query.isEqualTo(entry.key, entry.key)
        }
    }

    override fun toString(): String {
        return "ContactSpec(" +
                "content=$value, " +
                "type=$type, " +
                "userId=$user" +

                "sortOrder=$sortOrder, " +
                "sortBy=$sortBy, " +
                "operator=$operator, " +
                "page=$page, " +
                "size=$size" +
                ")"
    }

}