package eti.lucasgomes.mercadolivro.validation

import eti.lucasgomes.mercadolivro.exception.Error
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [BooksAvailableForPurchaseValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class BooksAvailableForPurchase(
        val message: String = Error.Book.BOOK_UNAVAILABLE_FOR_PURCHASE_MESSAGE,
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)
