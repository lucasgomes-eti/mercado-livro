package eti.lucasgomes.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import eti.lucasgomes.mercadolivro.enums.BookStatus
import eti.lucasgomes.mercadolivro.model.BookModel
import eti.lucasgomes.mercadolivro.model.CustomerModel
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PostBookRequest (
        @field:NotEmpty
        val name: String,

        @field:NotNull
        var price: BigDecimal,

        @JsonAlias("customer_id")
        val customerId: Int
) {
    fun toBookModel(customerModel: CustomerModel): BookModel {
        return BookModel(id = null, name = name, price = price, status = BookStatus.ATIVO, customer = customerModel)
    }
}
