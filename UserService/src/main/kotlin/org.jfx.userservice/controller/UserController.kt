package org.jfx.userservice.controller

import jakarta.validation.Valid
import org.jfx.userservice.model.User
import org.jfx.userservice.model.UserLoginDTO
import org.jfx.userservice.model.UserRegistrationDto
import org.jfx.userservice.security.jwt.JwtTokenUtil
import org.jfx.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val authenticationManager: AuthenticationManager
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registrationDto: UserRegistrationDto): ResponseEntity<String> {
        userService.registerUser(registrationDto)
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody @Valid loginDto: UserLoginDTO): ResponseEntity<Map<String, String>> {
        val authToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        val authentication = authenticationManager.authenticate(authToken)

        if (authentication.isAuthenticated) {
            val token = jwtTokenUtil.generateToken(authentication)
            return ResponseEntity.ok(mapOf("token" to token))
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid credentials"))
        }
    }

    @GetMapping("/me")
    fun getUserInfo(): ResponseEntity<User> {
        val currentUser = userService.getCurrentUser()
        return ResponseEntity.ok(currentUser)
    }
}
