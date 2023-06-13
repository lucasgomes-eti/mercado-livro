package eti.lucasgomes.mercadolivro.controller

import eti.lucasgomes.mercadolivro.controller.request.PostBookRequest
import eti.lucasgomes.mercadolivro.controller.request.PutBookRequest
import eti.lucasgomes.mercadolivro.controller.response.BookResponse
import eti.lucasgomes.mercadolivro.controller.response.PageResponse
import eti.lucasgomes.mercadolivro.extensions.toPageResponse
import eti.lucasgomes.mercadolivro.service.BookService
import eti.lucasgomes.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest) {
        val customerModel = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customerModel))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 20) pageable: Pageable): PageResponse<BookResponse> {
        return bookService.findAll(pageable).map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 20) pageable: Pageable): Page<BookResponse> {
        return bookService.findActives(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse {
        return bookService.findById(id).toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid putBookRequest: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.update(putBookRequest.toBookModel(bookSaved))
    }
}