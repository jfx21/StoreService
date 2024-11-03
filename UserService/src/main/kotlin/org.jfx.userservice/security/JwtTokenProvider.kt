package org.jfx.userservice.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    private val secretKey = "null_print(exit(0))" // to be secured later on
    private val validityInMs = 3600000 // 1 hour

    fun createToken(username: String): String {
        val claims = Jwts.claims().setSubject(username)
        val now = Date()
        val validity = Date(now.time + validityInMs)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }
}

