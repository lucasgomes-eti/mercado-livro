package eti.lucasgomes.mercadolivro.model

import eti.lucasgomes.mercadolivro.controller.response.BookResponse
import eti.lucasgomes.mercadolivro.enums.BookStatus
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column
        val name: String,

        @Column
        val price: BigDecimal,

        @Column
        @Enumerated(EnumType.STRING)
        val status: BookStatus? = null,

        @ManyToOne
        @JoinColumn(name = "customer_id")
        val customer: CustomerModel? = null
) {
        fun toResponse(): BookResponse {
                return BookResponse(id!!, name, price, customer, status)
        }
}