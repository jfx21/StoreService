package org.jfx.userservice.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @field:NotBlank
    @JsonProperty("username")
    val username: String,

    @field:Email
    @field:NotBlank
    @JsonProperty("email")
    val email: String,

    @field:Size(min = 6)
    @JsonProperty("password")
    val password: String
)

