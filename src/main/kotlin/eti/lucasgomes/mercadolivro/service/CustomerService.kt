package eti.lucasgomes.mercadolivro.service

import eti.lucasgomes.mercadolivro.enums.CustomerStatus
import eti.lucasgomes.mercadolivro.enums.Role
import eti.lucasgomes.mercadolivro.exception.Error
import eti.lucasgomes.mercadolivro.exception.NotFoundException
import eti.lucasgomes.mercadolivro.model.CustomerModel
import eti.lucasgomes.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun getCustomers(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow { NotFoundException(Error.Customer.NotFound(id)) }
    }

    fun createCustomer(customer: CustomerModel): CustomerModel {
        val newCustomer = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        return customerRepository.save(newCustomer)
    }

    fun updateCustomer(
        customerModel: CustomerModel
    ) {
        if (!customerRepository.existsById(customerModel.id!!)) {
            throw NotFoundException(Error.Customer.NotFound(customerModel.id))
        }
        customerRepository.save(customerModel)
    }

    fun deleteCustomer(id: Int) {
        val customerModel = findById(id)
        bookService.deleteByCustomer(customerModel)
        customerRepository.save(customerModel.copy(status = CustomerStatus.INATIVO))
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }
}