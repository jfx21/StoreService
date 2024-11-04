package org.jfx.userservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @field:NotBlank
    @JsonProperty("username")
    val username: String,

    @field:Email
    @field:NotBlank
    @JsonProperty("email")
    val email: String,

    @field:Size(min = 6)
    @JsonProperty("password")
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    name = "user_roles",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: Set<Role> = emptySet()
) : UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map{ GrantedAuthority { it.name }}
    }

    override fun getPassword(): String {
        return password //should be used with BCRYPT, remember about last OKTA accident
    }

    override fun getUsername(): String {
       return username
    }
    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

