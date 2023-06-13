package eti.lucasgomes.mercadolivro.config

import eti.lucasgomes.mercadolivro.enums.Role
import eti.lucasgomes.mercadolivro.repository.CustomerRepository
import eti.lucasgomes.mercadolivro.security.AuthenticationFilter
import eti.lucasgomes.mercadolivro.security.AuthorizationFilter
import eti.lucasgomes.mercadolivro.security.CustomAuthenticationEntryPoint
import eti.lucasgomes.mercadolivro.security.JwtUtil
import eti.lucasgomes.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val configuration: AuthenticationConfiguration,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
) {

    private val publicPostMatchers = arrayOf(
        "/customers"
    )

    private val adminMatchers = arrayOf(
        "/admin/**"
    )

    private val swaggerMatchers = arrayOf(
        "/v2/api-docs/**",
        "/v3/api-docs/**",
        "/configuration/ui",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/csrf/**"
    )

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun filter(httpSecurity: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        httpSecurity.cors { it.disable() }
        httpSecurity.csrf { it.disable() }
        httpSecurity.authorizeHttpRequests { configure ->
            configure.apply {
                requestMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
                requestMatchers(*adminMatchers).hasAuthority(Role.ADMIN.description)
                requestMatchers(*swaggerMatchers).permitAll()
                anyRequest().authenticated()
            }
        }
        httpSecurity.addFilter(AuthenticationFilter(authenticationManager, customerRepository, jwtUtil))
        httpSecurity.addFilter(AuthorizationFilter(authenticationManager, jwtUtil, userDetails))
        httpSecurity.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        httpSecurity.exceptionHandling { it.authenticationEntryPoint(customEntryPoint) }

        return httpSecurity.build()
    }

    @Bean
    fun corsConfig(): CorsFilter {
        return CorsFilter(UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration(
                "/**",
                CorsConfiguration().apply {
                    allowCredentials = true
                    addAllowedOriginPattern("*")
                    addAllowedHeader("*")
                    addAllowedMethod("*")
                }
            )
        })
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}