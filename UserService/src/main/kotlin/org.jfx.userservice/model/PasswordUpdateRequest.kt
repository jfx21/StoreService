package org.jfx.userservice.model

data class PasswordUpdateRequest(val currentPassword: String, val newPassword: String)
