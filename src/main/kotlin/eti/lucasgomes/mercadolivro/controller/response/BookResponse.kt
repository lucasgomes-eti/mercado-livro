package eti.lucasgomes.mercadolivro.controller.response

import eti.lucasgomes.mercadolivro.enums.BookStatus
import eti.lucasgomes.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse(
        val id: Int,
        val name: String,
        val price: BigDecimal,
        val customer: CustomerModel?,
        val status: BookStatus?
)
