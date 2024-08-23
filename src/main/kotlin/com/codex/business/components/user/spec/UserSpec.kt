package com.codex.business.components.user.spec

import com.codex.base.shared.Queries
import com.codex.base.shared.BaseSpec
import com.codex.business.components.contact.enum.ContactType
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import com.codex.business.components.user.repo.User
import io.quarkus.panache.common.Parameters
import jakarta.ws.rs.QueryParam
import java.time.LocalDate
import java.time.LocalDateTime

class UserSpec : BaseSpec() {
    @QueryParam("firstName")
    var firstName: String? = null

    @QueryParam("lastName")
    var lastName: String? = null

    @QueryParam("dateOfBirth")
    var dateOfBirth: LocalDate? = null

    @QueryParam("status")
    var status: UserStatus? = null

    @QueryParam("role")
    var role: UserRole? = null

    @QueryParam("contactType")
    var contactType: ContactType? = null

    @QueryParam("startDate")
    var startDate: LocalDateTime? = null

    @QueryParam("endDate")
    var endDate: LocalDateTime? = null

    @QueryParam("createdAt")
    var createdAt: LocalDateTime? = null

    @QueryParam("modifiedAt")
    var modifiedAt: LocalDateTime? = null

    override fun toParameters(): Parameters = Parameters
        .with(::firstName.name, firstName)
        .and(::lastName.name, lastName)
        .and(::dateOfBirth.name, dateOfBirth)
        .and(::role.name, role)
        .and(::status.name, status)
        .and(::contactType.name, contactType)
        .and(::createdAt.name, createdAt)
        .and(::modifiedAt.name, modifiedAt)
        .and(::startDate.name, startDate)
        .and(::endDate.name, endDate)


    override fun queryDefinition(entry: Map.Entry<String, Any?>): String {
        return when (entry.key) {
            ::firstName.name, ::lastName.name -> Queries.contains(entry.key, entry.key)
            ::contactType.name -> Queries.isIn(User::contacts.name, Contact::type.name, entry.key)
            ::startDate.name -> Queries.isGreaterThanOrEqualTo(User::createdAt.name, entry.key)
            ::endDate.name -> Queries.isLessThanOrEqualTo(User::createdAt.name, entry.key)
            else -> Queries.isEqualTo(entry.key, entry.key)
        }
    }

    override fun toString(): String {
        return "UserSpec(" +
                "firstName=$firstName, " +
                "lastName=$lastName, " +
                "dateOfBirth=$dateOfBirth, " +
                "status=$status, " +
                "role=$role, " +
                "contactType=$contactType, " +
                "startDate=$startDate, " +
                "endDate=$endDate, " +
                "createdAt=$createdAt, " +
                "modifiedAt=$modifiedAt, " +

                "sortOrder=$sortOrder, " +
                "sortBy=$sortBy, " +
                "operator=$operator, " +
                "page=$page, " +
                "size=$size" +
                ")"
    }
}