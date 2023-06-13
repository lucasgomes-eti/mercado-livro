package eti.lucasgomes.mercadolivro.validation

import eti.lucasgomes.mercadolivro.service.BookService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class BooksAvailableForPurchaseValidator(private val bookService: BookService): ConstraintValidator<BooksAvailableForPurchase, Set<Int>> {

    override fun isValid(value: Set<Int>?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()) {
            return false
        }
        return bookService.areAllTheBooksActive(value)
    }

}
