package org.jfx.userservice.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenUtil(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long
) {

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        // Collect roles to add to token
        val roles = userPrincipal.authorities.map { it.authority }

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (ex: Exception) {
            // Handle exceptions like ExpiredJwtException, UnsupportedJwtException, etc.
            println("Invalid JWT token: ${ex.message}") //TODO change to logger when configured
        }
        return false
    }
}

