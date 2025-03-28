package org.jfx.userservice.security.config

import org.jfx.userservice.security.UserDetailsServiceImpl
import org.jfx.userservice.security.jwt.JwtTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val jwtTokenFilter: JwtTokenFilter,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {
    private val WHITE_LIST = arrayOf(
        "/api/users/login", "/api/users/register",
        "/swagger-ui.html", "/v3/api-docs/**"
    )

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {
                csrf -> csrf.disable()
            }
            .cors { corsConfigurationSource() }
            .sessionManagement { sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auths ->
                auths
                    .requestMatchers(*WHITE_LIST).permitAll()
//                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @Bean
    open fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }
    @Bean
    open fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedMethods = listOf("GET", "POST", "DELETE", "PUT", "OPTIONS")
        configuration.allowedOrigins = listOf("http://localhost:3000")  // Your frontend URL
        configuration.allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With")
        configuration.allowCredentials = true
        configuration.exposedHeaders = listOf("Authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}


