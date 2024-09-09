package com.codex.business.components.user.repo

import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_firstName", columnList = "firstName"),
        Index(name = "idx_lastName", columnList = "lastName"),
        Index(name = "idx_dateOfBirth", columnList = "dateOfBirth"),
        Index(name = "idx_status", columnList = "status"),
        Index(name = "idx_role", columnList = "role"),
        Index(name = "idx_user_createdAt", columnList = "createdAt"),
        Index(name = "idx_user_modifiedAt", columnList = "modifiedAt"),
    ]
)
//@EntityListeners(UserEntityListener::class)
class User : PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    var firstName: String? = null

    var lastName: String? = null

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    var contacts: MutableSet<Contact> = mutableSetOf()

    var isEnabled: Boolean? = true

    var dateOfBirth: LocalDate? = null

    @Enumerated(value = EnumType.STRING)
    var status: UserStatus? = UserStatus.ALIVE

    @Enumerated(value = EnumType.STRING)
    var role: UserRole? = UserRole.USER

    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var modifiedAt: LocalDateTime? = null

    @Version
    val version: Long? = null

    override fun toString(): String {
        return "User(" +
                "id=$id, " +
                "firstName=$firstName, " +
                "lastName=$lastName, " +
                "isEnabled=$isEnabled, " +
                "contacts=$contacts, " +
                "dateOfBirth=$dateOfBirth, " +
                "status=$status, " +
                "role=$role, " +
                "createdAt=$createdAt, " +
                "modifiedAt=$modifiedAt, " +
                "version=$version" +
                ")"
    }
}