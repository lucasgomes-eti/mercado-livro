package eti.lucasgomes.mercadolivro.service

import eti.lucasgomes.mercadolivro.enums.BookStatus
import eti.lucasgomes.mercadolivro.exception.BadRequestException
import eti.lucasgomes.mercadolivro.exception.Error
import eti.lucasgomes.mercadolivro.exception.NotFoundException
import eti.lucasgomes.mercadolivro.model.BookModel
import eti.lucasgomes.mercadolivro.model.CustomerModel
import eti.lucasgomes.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun create(bookModel: BookModel) {
        bookRepository.save(bookModel)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(pageable, BookStatus.ATIVO)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow { NotFoundException(Error.Book.NotFound(id)) }
    }

    fun delete(id: Int) {
        val book = findById(id)
        bookRepository.save(book.copy(status = BookStatus.CANCELADO))
    }

    fun update(bookModel: BookModel) {
        if (bookModel.status == BookStatus.CANCELADO || bookModel.status == BookStatus.DELETADO) {
            throw BadRequestException(Error.Book.InvalidStatus(bookModel.status.name))
        }
        bookRepository.save(bookModel)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        val booksToBeSaved = mutableListOf<BookModel>()

        books.forEach { book ->
            booksToBeSaved.add(book.copy(status = BookStatus.DELETADO))
        }

        bookRepository.saveAll(booksToBeSaved)
    }

    fun findAllByIds(bookIds: Set<Int>): MutableList<BookModel> {
        return bookRepository.findAllById(bookIds)
    }

    fun purchase(books: MutableList<BookModel>) {
        val newBooks = books.map { it.copy(status = BookStatus.VENDIDO) }
        bookRepository.saveAll(newBooks)
    }

    fun areAllTheBooksActive(booksIds: Set<Int>): Boolean {
        return !findAllByIds(booksIds).any { it.status != BookStatus.ATIVO }
    }
}
