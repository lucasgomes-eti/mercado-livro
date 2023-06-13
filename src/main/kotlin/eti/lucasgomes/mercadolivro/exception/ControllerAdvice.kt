package eti.lucasgomes.mercadolivro.exception

import eti.lucasgomes.mercadolivro.controller.response.ErrorResponse
import eti.lucasgomes.mercadolivro.controller.response.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.security.access.AccessDeniedException

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
                httpCode = HttpStatus.NOT_FOUND.value(),
                message = exception.error.message,
                internalCode = exception.error.code,
                errors = emptyList()
        )
        return ResponseEntity(error, HttpStatusCode.valueOf(error.httpCode))
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
                httpCode = HttpStatus.BAD_REQUEST.value(),
                message = exception.error.message,
                internalCode = exception.error.code,
                errors = emptyList()
        )
        return ResponseEntity(error, HttpStatusCode.valueOf(error.httpCode))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
                httpCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
                message = Error.Global.InvalidEntity.message,
                internalCode = Error.Global.InvalidEntity.code,
                errors = exception.bindingResult.fieldErrors.map { FieldErrorResponse(it.field, it.defaultMessage ?: "Invalid") }
        )
        return ResponseEntity(error, HttpStatusCode.valueOf(error.httpCode))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleMethodArgumentNotValidException(exception: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httpCode = HttpStatus.FORBIDDEN.value(),
            message = Error.Auth.AccessDenied.message,
            internalCode = Error.Auth.AccessDenied.code,
            errors = emptyList()
        )
        return ResponseEntity(error, HttpStatusCode.valueOf(error.httpCode))
    }
}