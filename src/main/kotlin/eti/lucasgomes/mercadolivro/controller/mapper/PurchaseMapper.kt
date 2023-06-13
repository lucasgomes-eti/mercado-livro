package eti.lucasgomes.mercadolivro.controller.mapper

import eti.lucasgomes.mercadolivro.controller.request.PostPurchaseRequest
import eti.lucasgomes.mercadolivro.model.PurchaseModel
import eti.lucasgomes.mercadolivro.service.BookService
import eti.lucasgomes.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
        private val bookService: BookService,
        private val customerService: CustomerService
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.findById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)
        return PurchaseModel(
                customer = customer,
                books = books,
                price = books.sumOf { it.price }
        )
    }
}