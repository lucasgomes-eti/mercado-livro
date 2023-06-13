package eti.lucasgomes.mercadolivro.validation

import eti.lucasgomes.mercadolivro.exception.Error
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [EmailAvailableValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class EmailAvailable(
        val message: String = Error.Global.EMAIL_TAKEN_MESSAGE,
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)
