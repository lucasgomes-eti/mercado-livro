package eti.lucasgomes.mercadolivro.security

import eti.lucasgomes.mercadolivro.enums.CustomerStatus
import eti.lucasgomes.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(
    private val customer: CustomerModel
): UserDetails {

    val id = customer.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return customer.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()
    }

    override fun getPassword(): String {
        return customer.password
    }

    override fun getUsername(): String {
        return customer.id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return customer.status == CustomerStatus.ATIVO
    }
}