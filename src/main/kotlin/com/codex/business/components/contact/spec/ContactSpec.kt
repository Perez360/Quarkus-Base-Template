package com.codex.business.components.contact.spec

import com.codex.base.shared.BaseSpec
import com.codex.base.shared.Queries
import com.codex.business.components.contact.enum.ContactType
import io.quarkus.panache.common.Parameters
import jakarta.ws.rs.QueryParam

class ContactSpec : BaseSpec() {
    @QueryParam(value = "content")
    var content: String? = null

    @QueryParam(value = "type")
    var type: ContactType? = null

    @QueryParam(value = "userId")
    var user: String? = null

    override fun toParameters(): Parameters {
        return Parameters.with(::content.name, content)
            .and(::content.name, content)
            .and(::type.name, type)
            .and(::user.name, user)
    }

    override fun queryDefinition(entry: Map.Entry<String, Any?>): String {
        return when (entry.key) {
            ::content.name -> Queries.contains(entry.key, entry.key)
            ::user.name -> Queries.isEqualTo(entry.key + ".id", entry.key)
            else -> Queries.isEqualTo(entry.key, entry.key)
        }
    }

    override fun toString(): String {
        return "ContactSpec(" +
                "content=$content, " +
                "type=$type, " +
                "userId=$user, " +

                "sortOrder=$sortOrder, " +
                "sortBy=$sortBy, " +
                "operator=$operator, " +
                "page=$page, " +
                "size=$size" +
                ")"
    }

}