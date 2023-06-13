package eti.lucasgomes.mercadolivro.controller.request

import eti.lucasgomes.mercadolivro.model.BookModel
import jakarta.validation.constraints.NotEmpty
import java.math.BigDecimal

data class PutBookRequest (

        @field:NotEmpty
        val name: String?,

        val price: BigDecimal?
) {
    fun toBookModel(previousValue: BookModel): BookModel {
        return BookModel(
                id = previousValue.id,
                name = name ?: previousValue.name,
                price = price ?: previousValue.price,
                status = previousValue.status,
                customer = previousValue.customer
        )
    }
}
