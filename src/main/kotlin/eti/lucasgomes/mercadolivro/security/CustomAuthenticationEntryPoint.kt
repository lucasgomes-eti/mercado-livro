package eti.lucasgomes.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eti.lucasgomes.mercadolivro.controller.response.ErrorResponse
import eti.lucasgomes.mercadolivro.exception.Error
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            Error.Auth.Unauthorized.message,
            Error.Auth.Unauthorized.code,
            emptyList()
        )
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}