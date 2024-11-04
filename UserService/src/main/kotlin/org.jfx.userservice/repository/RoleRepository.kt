package org.jfx.userservice.repository

import org.jfx.userservice.model.RoleDTO
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleDTO,Long>{
    fun findByName(name: String) : RoleDTO?
}