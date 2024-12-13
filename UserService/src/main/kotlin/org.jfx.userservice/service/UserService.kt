package org.jfx.userservice.service

import org.jfx.userservice.exception.FailedToChangePasswordException
import org.jfx.userservice.exception.PasswordIsNotValidException
import org.jfx.userservice.exception.UserDataIsNotValidException
import org.jfx.userservice.model.PasswordUpdateRequest
import org.jfx.userservice.model.Role
import org.jfx.userservice.model.User
import org.jfx.userservice.model.dto.UserRegistrationDto
import org.jfx.userservice.repository.UserRepository
import org.jfx.userservice.security.jwt.JwtTokenUtil
import org.jfx.userservice.util.JsonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userDataValidationService: UserDataValidationService,
    private val jwtTokenUtil: JwtTokenUtil
) {
    private val jsonUtil: JsonUtil = JsonUtil()
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    open fun registerUser(registrationDto: UserRegistrationDto) {
        val validationResult = userDataValidationService.validateUserInputs(registrationDto)
        val encodedPassword = passwordEncoder.encode(registrationDto.password)
        if (validationResult.isUserInputCorrect) {
            val user = User(
                username = registrationDto.username,
                email = registrationDto.email,
                phoneNumber = registrationDto.phoneNumber,
                password = encodedPassword,
                roles = setOf(Role.USER)
            )
            logger.info("User registered successfully")
            userRepository.save(user)
        } else {
            throw UserDataIsNotValidException(jsonUtil.toJson(validationResult))
        }
    }
    @Transactional
    open fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found $username")
    }
    @Transactional
    open fun updatePassword(req: PasswordUpdateRequest){
        val passCheck = userDataValidationService.isPasswordCorrect(req.newPassword)
        if(passCheck){
            var currentUser = getCurrentUser()
            if(!passwordEncoder.matches(req.currentPassword, currentUser.password))
                throw PasswordIsNotValidException("Current password is not valid...")
            currentUser.password = passwordEncoder.encode(req.newPassword)
            userRepository.save(currentUser)
        }else{
            throw FailedToChangePasswordException("Failed to change password...Please try again.")
        }
    }

    @Transactional
    open fun deleteUser(jwtToken: String) :Boolean {
        val date = LocalDateTime.now()
        if(jwtTokenUtil.validateToken(jwtToken)){
            val username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
            userRepository.delete(user)
            logger.info("[Username] = $username deleted successfully, [timestamp] = $date")
            return true
        }
        return false
    }
}

