package eti.lucasgomes.mercadolivro.controller.response

import eti.lucasgomes.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    val id: Int,
    val name: String,
    val email: String,
    val status: CustomerStatus
)
