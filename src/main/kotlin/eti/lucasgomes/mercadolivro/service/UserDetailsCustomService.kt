package eti.lucasgomes.mercadolivro.service

import eti.lucasgomes.mercadolivro.exception.AuthenticationException
import eti.lucasgomes.mercadolivro.exception.Error
import eti.lucasgomes.mercadolivro.repository.CustomerRepository
import eti.lucasgomes.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException(Error.Auth.Unauthorized) }
        return UserCustomDetails(customer)
    }
}