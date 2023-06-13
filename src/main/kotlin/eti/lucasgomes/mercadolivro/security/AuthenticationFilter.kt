package eti.lucasgomes.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eti.lucasgomes.mercadolivro.controller.request.LoginRequest
import eti.lucasgomes.mercadolivro.exception.AuthenticationException
import eti.lucasgomes.mercadolivro.exception.Error
import eti.lucasgomes.mercadolivro.repository.CustomerRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val customerId = customerRepository.findByEmail(loginRequest.email)?.id
            val authenticationToken = UsernamePasswordAuthenticationToken(customerId, loginRequest.password)
            return authenticationManager.authenticate(authenticationToken)
        } catch (e: Exception) {
            throw AuthenticationException(Error.Auth.Unauthorized)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }
}