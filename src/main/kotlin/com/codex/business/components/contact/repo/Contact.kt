package com.codex.business.components.contact.repo

import com.codex.business.components.contact.enum.ContactType
import com.codex.business.components.user.repo.User
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@Entity
@Table(
    name = "contacts",
    indexes = [
        Index(name = "idx_value", columnList = "content"),
        Index(name = "idx_type", columnList = "type"),
        Index(name = "idx_user", columnList = "userId"),
        Index(name = "idx_contact_createdAt", columnList = "createdAt"),
        Index(name = "idx_contact_modifiedAt", columnList = "modifiedAt"),
    ]
)
data class Contact(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var content: String? = null,

    @Enumerated(value = EnumType.STRING)
    var type: ContactType? = null,

    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User? = null,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    val modifiedAt: LocalDateTime? = null,

    @Version
    val version: Long? = null

) : PanacheEntityBase {
    override fun toString(): String {
        return "Contact(" +
                "id=$id, " +
                "content=$content, " +
                "type=$type, " +
                "user=$user, " +
                "createdAt=$createdAt, " +
                "modifiedAt=$modifiedAt, " +
                "version=$version" +
                ")"
    }
}