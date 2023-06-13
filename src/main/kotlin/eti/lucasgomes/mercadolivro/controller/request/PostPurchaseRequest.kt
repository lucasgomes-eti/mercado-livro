package eti.lucasgomes.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import eti.lucasgomes.mercadolivro.validation.BooksAvailableForPurchase
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PostPurchaseRequest (
        @field:NotNull
        @field:Positive
        @JsonAlias("customer_id")
        val customerId: Int,

        @field:NotNull
        @JsonAlias("book_ids")
        @BooksAvailableForPurchase
        val bookIds: Set<Int>
)