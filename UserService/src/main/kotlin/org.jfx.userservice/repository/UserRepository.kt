package org.jfx.userservice.repository

import org.jfx.userservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("UserRepository")
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findUserById(id: Long): User?
    fun findByEmail(email: String): User?
    fun findByPhoneNumber(phoneNumber: String): User?
}
