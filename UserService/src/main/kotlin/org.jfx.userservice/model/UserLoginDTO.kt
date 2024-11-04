package org.jfx.userservice.model

import jakarta.validation.constraints.NotBlank

data class UserLoginDTO(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)