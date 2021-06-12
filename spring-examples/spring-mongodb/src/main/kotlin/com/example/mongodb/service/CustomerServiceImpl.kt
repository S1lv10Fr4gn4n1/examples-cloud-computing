package com.example.mongodb.service

import com.example.mongodb.repository.Customer
import com.example.mongodb.repository.CustomerRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val repository: CustomerRepository) : CustomerService {

    override fun addCustomer(customer: Customer) {
        repository.insert(customer)
    }

    override fun getAllCustomers(): List<Customer> {
        return repository.findAll()
    }

    override fun getByLastName(lastName: String): List<Customer> {
        return repository.findByLastName(lastName)
    }
}