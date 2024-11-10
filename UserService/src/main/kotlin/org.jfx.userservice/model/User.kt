package org.jfx.userservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "users")
@Getter
class User() : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @field:NotBlank
    @JsonProperty("username")
    private var username: String = ""

    @field:Email
    @field:NotBlank
    var email: String = ""

    var phoneNumber:String = ""

    @field:Size(min = 6)
    private var password: String = ""

    var roles: Set<Role> = emptySet()



    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    constructor(username: String, password: String, email: String,phoneNumber: String, roles: Set<Role>) : this() {
        id = null
        this.username = username
        this.password = password
        this.email = email
        this.phoneNumber = phoneNumber
        this.roles = roles
    }
}

