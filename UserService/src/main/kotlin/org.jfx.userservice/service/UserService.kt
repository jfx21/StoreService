package org.jfx.userservice.service

import org.jfx.userservice.model.User
import org.jfx.userservice.repository.RoleRepository
import org.jfx.userservice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(username: String, password: String) {
        val userRole = roleRepository.findByName("USER")
            ?: throw IllegalArgumentException("Default role not found")

        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            roles = setOf(userRole)
        )

        userRepository.save(user)
    }

    fun assignRoleToUser(username: String, roleName: String) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        val role = roleRepository.findByName(roleName)
            ?: throw IllegalArgumentException("Role not found")

        user.roles += role
        userRepository.save(user)
    }
}

