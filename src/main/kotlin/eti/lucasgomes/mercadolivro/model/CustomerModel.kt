package eti.lucasgomes.mercadolivro.model

import eti.lucasgomes.mercadolivro.controller.response.CustomerResponse
import eti.lucasgomes.mercadolivro.enums.CustomerStatus
import eti.lucasgomes.mercadolivro.enums.Role
import jakarta.persistence.*

@Entity(name = "customer")
data class CustomerModel(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column
        val name: String,

        @Column
        val email: String,

        @Column
        @Enumerated(EnumType.STRING)
        val status: CustomerStatus,

        @Column
        val password: String,

        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
        @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
        val roles: Set<Role> = emptySet()

) {
        fun toResponse(): CustomerResponse {
                return CustomerResponse(id!!, name, email, status)
        }
}