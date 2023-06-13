package eti.lucasgomes.mercadolivro.controller.request

import eti.lucasgomes.mercadolivro.enums.CustomerStatus
import eti.lucasgomes.mercadolivro.model.CustomerModel
import eti.lucasgomes.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(

    @field:NotEmpty
    val name: String,

    @field:Email
    @EmailAvailable
    val email: String,

    @field:NotEmpty
    val password: String

) {
    fun toCustomerModel(): CustomerModel {
        return CustomerModel(id = null, name = name, email = email, status = CustomerStatus.ATIVO, password = password)
    }
}
