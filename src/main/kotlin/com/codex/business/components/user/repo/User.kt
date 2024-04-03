package com.codex.business.components.user.repo

import com.codex.business.components.contact.repo.Contact
import com.codex.business.components.user.enum.UserRole
import com.codex.business.components.user.enum.UserStatus
import com.fasterxml.jackson.annotation.JsonIgnore
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
        Index(name = "idx_dob", columnList = "dob"),
        Index(name = "idx_status", columnList = "status"),
        Index(name = "idx_role", columnList = "role"),
        Index(name = "idx_user_createdAt", columnList = "createdAt"),
        Index(name = "idx_user_updatedAt", columnList = "updatedAt"),
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var firstName: String? = null,

    var lastName: String? = null,

    @JsonIgnore
    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    var contacts: MutableList<Contact>? = null,

    var dob: LocalDate? = null,

    @Enumerated(value = EnumType.STRING)
    var status: UserStatus? = UserStatus.ALIVE,

    @Enumerated(value = EnumType.STRING)
    var role: UserRole? = UserRole.USER,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,

    @Version
    val version: Long? = null

) : PanacheEntityBase