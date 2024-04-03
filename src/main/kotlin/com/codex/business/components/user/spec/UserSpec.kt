package com.codex.business.components.user.spec

import com.codex.base.shared.Query
import com.codex.base.shared.Spec
import com.codex.business.components.contact.enum.ContactType
import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import com.codex.business.components.user.repo.User
import io.quarkus.panache.common.Parameters
import jakarta.ws.rs.QueryParam
import java.time.LocalDate
import java.time.LocalDateTime

class UserSpec : Spec() {
    @QueryParam("firstName")
    var firstName: String? = null

    @QueryParam("lastName")
    var lastName: String? = null

    @QueryParam("dob")
    var dob: LocalDate? = null

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

    @QueryParam("updatedAt")
    var updatedAt: LocalDateTime? = null

    override fun toParameters(): Parameters = Parameters
        .with(::firstName.name, firstName)
        .and(::lastName.name, lastName)
        .and(::dob.name, dob)
        .and(::role.name, role)
        .and(::status.name, status)
        .and(::contactType.name, contactType)
        .and(::createdAt.name, createdAt)
        .and(::updatedAt.name, updatedAt)
        .and(::startDate.name, startDate)
        .and(::endDate.name, endDate)


    override fun queryDefinition(entry: Map.Entry<String, Any?>): String {
        return when (entry.key) {
            ::firstName.name, ::lastName.name -> Query.contains(entry.key, entry.key)
            ::contactType.name -> Query.isIn(User::contacts.name, Contact::type.name, entry.key)
            ::startDate.name -> Query.isGreaterThanOrEqualTo(User::createdAt.name, entry.key)
            ::endDate.name -> Query.isLessThanOrEqualTo(User::createdAt.name, entry.key)
            else -> Query.isEqualTo(entry.key, entry.key)
        }
    }

    override fun toString(): String {
        return "UserSpec(" +
                "firstName=$firstName, " +
                "lastName=$lastName, " +
                "dob=$dob, " +
                "status=$status, " +
                "role=$role, " +
                "contactType=$contactType, " +
                "startDate=$startDate, " +
                "endDate=$endDate, " +
                "createdAt=$createdAt, " +
                "updatedAt=$updatedAt, " +

                "sortOrder=$sortOrder, " +
                "sortBy=$sortBy, " +
                "operator=$operator, " +
                "page=$page, " +
                "size=$size" +
                ")"
    }
}