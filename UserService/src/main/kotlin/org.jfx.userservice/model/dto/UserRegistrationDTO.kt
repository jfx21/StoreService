package org.jfx.userservice.model.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRegistrationDto(
    @field:NotBlank
    val username: String,

    @field:NotBlank
    @field:Size(min = 6)
    val password: String,

    @field:Email
    @field:NotBlank
    val email: String
)
