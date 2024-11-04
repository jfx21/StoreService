package org.jfx.userservice.service

import org.jfx.userservice.model.User
import org.jfx.userservice.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun createUser(user: User): User = userRepository.save(user)

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}
