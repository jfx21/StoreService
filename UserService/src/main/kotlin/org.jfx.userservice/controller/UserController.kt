package org.jfx.userservice.controller

import jakarta.validation.Valid
import org.jfx.userservice.model.PasswordUpdateRequest
import org.jfx.userservice.model.User
import org.jfx.userservice.model.dto.UserLoginDTO
import org.jfx.userservice.model.dto.UserRegistrationDto
import org.jfx.userservice.security.jwt.JwtTokenUtil
import org.jfx.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["http://localhost:3000"])
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
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginDto.username,
                    loginDto.password
                )
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwtToken = jwtTokenUtil.generateToken(authentication)
           ResponseEntity.status(HttpStatus.OK).body(mapOf("token" to jwtToken))
        }    catch (ex : AuthenticationException){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid credentials"))
        }
    }

    @DeleteMapping("/delete")
    fun deleteUser(@RequestBody @Valid jwtToken: String): ResponseEntity<String>{
        val result = userService.deleteUser(jwtToken)
        if(result)
            return ResponseEntity.ok("User deletion successfully...")
        return ResponseEntity.badRequest().body("User deletion failed...")
    }
    @PutMapping("/me/password")
    fun updateUserPassword(@RequestBody passwordUpdateRequest: PasswordUpdateRequest): ResponseEntity<Any>{
        try {
            userService.updatePassword(passwordUpdateRequest)
            return ResponseEntity.ok("Password has been updated successfully...")
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(ex.message)
        }
    }
    @GetMapping("/me")
    fun getUserInfo(): ResponseEntity<User> {
        val currentUser = userService.getCurrentUser()
        println(currentUser.username)
        return ResponseEntity.ok(currentUser)
    }
}
