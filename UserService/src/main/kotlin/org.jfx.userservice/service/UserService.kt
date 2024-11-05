package org.jfx.userservice.service

import org.jfx.userservice.exception.RoleNotFoundException
import org.jfx.userservice.model.User
import org.jfx.userservice.model.UserRegistrationDto
import org.jfx.userservice.repository.RoleRepository
import org.jfx.userservice.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(registrationDto: UserRegistrationDto) {
        val encodedPassword = passwordEncoder.encode(registrationDto.password)

        val userRole = roleRepository.findByName("USER")
            ?: throw IllegalArgumentException("Default role not found")

        val user = User(
            username = registrationDto.username,
            email = registrationDto.email,
            password = encodedPassword,
            roles = setOf(userRole)
        )

        userRepository.save(user)
    }

    fun assignRoleToUser(username: String, roleName: String) {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        val role = roleRepository.findByName(roleName)
            ?: throw RoleNotFoundException("Role not found")

        user.roles += role
        userRepository.save(user)
    }

    fun findByUsername(username: String) = userRepository.findByUsername(username)
    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }

}

