package eti.lucasgomes.mercadolivro.repository

import eti.lucasgomes.mercadolivro.util.ModelBuilder
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return name containing`() {
        val marcos = customerRepository.save(ModelBuilder.buildCustomer(name = "Marcos"))
        val mathew = customerRepository.save(ModelBuilder.buildCustomer(name = "Mathew"))

        val customersList = customerRepository.findByNameContaining("Ma")

        assertEquals(listOf(marcos, mathew), customersList)
    }

    @Nested
    inner class  `exists by email` {
        @Test
        fun `should return true when email exists`() {
            val email = "email@test.com"
            customerRepository.save(ModelBuilder.buildCustomer(email = email))
            val exists = customerRepository.existsByEmail(email)
            assertTrue(exists)
        }

        @Test
        fun `should return false when email do not exists`() {
            val email = "nonexistingemail@test.com"
            val exists = customerRepository.existsByEmail(email)
            assertFalse(exists)
        }
    }

    @Nested
    inner class  `find by email` {
        @Test
        fun `should return customer when email exists`() {
            val email = "email@test.com"
            val customer = customerRepository.save(ModelBuilder.buildCustomer(email = email))
            val customerResult = customerRepository.findByEmail(email)
            assertNotNull(customerResult)
            assertEquals(customer, customerResult)
        }

        @Test
        fun `should return null when email do not exists`() {
            val email = "nonexistingemail@test.com"
            val customerResult = customerRepository.findByEmail(email)
            assertNull(customerResult)
        }
    }
}