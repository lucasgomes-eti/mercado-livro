package eti.lucasgomes.mercadolivro.util

import eti.lucasgomes.mercadolivro.enums.CustomerStatus
import eti.lucasgomes.mercadolivro.enums.Role
import eti.lucasgomes.mercadolivro.model.BookModel
import eti.lucasgomes.mercadolivro.model.CustomerModel
import eti.lucasgomes.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.util.*

object ModelBuilder {
    fun buildCustomer(
        id: Int? = null,
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ): CustomerModel {
        return CustomerModel(
            id = id,
            name = name,
            email = email,
            status = CustomerStatus.ATIVO,
            password = password,
            roles = setOf(Role.CUSTOMER)
        )
    }

    fun buildPurchase(
        id: Int? = null,
        customer: CustomerModel = buildCustomer(),
        books: MutableList<BookModel> = mutableListOf(),
        nfe: String = UUID.randomUUID().toString(),
        price: BigDecimal = Random().nextDouble().toBigDecimal()
    ): PurchaseModel {
        return PurchaseModel(
            id = id, customer = customer, books = books, nfe = nfe, price = price
        )
    }
}