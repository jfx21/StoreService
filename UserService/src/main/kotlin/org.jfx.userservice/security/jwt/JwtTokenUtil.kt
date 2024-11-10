package org.jfx.userservice.security.jwt

import io.jsonwebtoken.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.Date
import javax.crypto.SecretKey

@Component
@Slf4j
class JwtTokenUtil(
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long
) {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)

    private fun getSigningKey(): SecretKey {
        return Jwts.SIG.HS512.key().build()
    }

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        // Collect roles to add to token
        val roles = userPrincipal.authorities.map { it.authority }

        return Jwts.builder()
            .subject(userPrincipal.username)
            .claim("roles", roles)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).payload.subject

    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
            return true
        } catch (ex: Exception) {
            when (ex) {
                is ExpiredJwtException -> {
                    logger.error("JWT token expired: ${ex.message}")
                }

                is MalformedJwtException -> {
                    logger.error("Invalid JWT token: ${ex.message}")
                }

                is SignatureException -> {
                    logger.error("Invalid JWT signature: ${ex.message}")
                }

                is UnsupportedJwtException -> {
                    logger.error("Unsupported JWT token: ${ex.message}")
                }

                is IllegalArgumentException -> {
                    logger.error("JWT claims string is empty: ${ex.message}")
                }

            }
        }
        return false
    }
}

