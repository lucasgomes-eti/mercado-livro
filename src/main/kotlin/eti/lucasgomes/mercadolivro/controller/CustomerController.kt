package eti.lucasgomes.mercadolivro.controller

import eti.lucasgomes.mercadolivro.controller.request.PostCustomerRequest
import eti.lucasgomes.mercadolivro.controller.request.PutCustomerRequest
import eti.lucasgomes.mercadolivro.controller.response.CustomerResponse
import eti.lucasgomes.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import eti.lucasgomes.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping
    fun getCustomers(@RequestParam name: String?): List<CustomerResponse> =
        customerService.getAll(name).map { it.toResponse() }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomer(@PathVariable id: Int): CustomerResponse? = customerService.findById(id).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(
        @RequestBody @Valid postCustomerRequest: PostCustomerRequest
    ): CustomerResponse = customerService.createCustomer(postCustomerRequest.toCustomerModel()).toResponse()

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(
        @PathVariable id: Int,
        @RequestBody @Valid putCustomerRequest: PutCustomerRequest
    ) {
        val customerSaved = customerService.findById(id)
        customerService.updateCustomer(putCustomerRequest.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) = customerService.deleteCustomer(id)
}