package org.jfx.userservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class RoleDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    var name: String = "" //name for ROLE, USER, ADMIN, OWNER
)
enum class Role {
    USER,
    ADMIN
}