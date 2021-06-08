package com.example.jpa.service

import com.example.jpa.repository.Address
import com.example.jpa.repository.AddressRepository
import com.example.jpa.repository.Customer
import com.example.jpa.repository.CustomerRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val addressRepository: AddressRepository,
    private val customerRepository: CustomerRepository
) : CustomerService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun addCustomer(customer: Customer) {
        customerRepository.saveAndFlush(customer)
    }

    override fun getAllCustomers(): List<Customer> {
        val sort = Sort.by(Sort.Direction.ASC, SORT_LAST_NAME)
        return customerRepository.findAll(sort).toList()
    }

    override fun getCustomerByLastName(lastName: String): Customer {
        return customerRepository.findByLastName(lastName)
    }

    override fun findAllCustomerLikeQuery(lastName: String): List<Customer> {
        return customerRepository.findAllCustomerLikeQuery(lastName)
    }

    override fun findByLastNameContaining(lastName: String): List<Customer> {
        return customerRepository.findByLastNameContaining(lastName)
    }

    override fun getCustomersPaginated(first: Int, max: Int): List<Customer> {
        val criteriaQuery = entityManager.criteriaBuilder.createQuery(Customer::class.java)
            .also { it.from(Customer::class.java) }
        return entityManager.createQuery(criteriaQuery).apply {
            firstResult = first
            maxResults = max
        }.resultList
    }

    override fun addAddress(address: Address) {
        addressRepository.save(address)
    }

    companion object {
        private const val SORT_LAST_NAME = "lastName"
    }
}