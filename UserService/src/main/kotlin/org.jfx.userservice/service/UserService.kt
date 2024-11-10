package org.jfx.userservice.service

import org.jfx.userservice.exception.UserDataIsNotValidException
import org.jfx.userservice.model.Role
import org.jfx.userservice.model.User
import org.jfx.userservice.model.dto.UserRegistrationDto
import org.jfx.userservice.repository.UserRepository
import org.jfx.userservice.util.JsonUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userDataValidationService: UserDataValidationService
) {
    private val jsonUtil: JsonUtil = JsonUtil()
    fun registerUser(registrationDto: UserRegistrationDto) {
        val encodedPassword = passwordEncoder.encode(registrationDto.password)
        val validationResult = userDataValidationService.validateUserInputs(registrationDto)
        if (validationResult.isUserInputCorrect) {
            val user = User(
                username = registrationDto.username,
                email = registrationDto.email,
                phoneNumber = registrationDto.phoneNumber,
                password = encodedPassword,
                roles = setOf(Role.USER)
            )

            userRepository.save(user)
        } else {
            throw UserDataIsNotValidException(jsonUtil.toJson(validationResult))
        }
    }

    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }

}

