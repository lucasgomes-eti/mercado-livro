package eti.lucasgomes.mercadolivro.controller.request

import eti.lucasgomes.mercadolivro.model.CustomerModel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PutCustomerRequest(
    @field:NotEmpty
    val name: String?,

    @field:Email
    val email: String?
) {
    fun toCustomerModel(previousCustomer: CustomerModel): CustomerModel {
        return CustomerModel(
            id = previousCustomer.id,
            name = name ?: previousCustomer.name,
            email = email ?: previousCustomer.email,
            status = previousCustomer.status,
            password = previousCustomer.password
        )
    }
}
