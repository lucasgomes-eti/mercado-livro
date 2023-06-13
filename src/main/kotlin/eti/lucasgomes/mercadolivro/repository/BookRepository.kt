package eti.lucasgomes.mercadolivro.repository

import eti.lucasgomes.mercadolivro.enums.BookStatus
import eti.lucasgomes.mercadolivro.model.BookModel
import eti.lucasgomes.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByStatus(pageable: Pageable, status: BookStatus): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}