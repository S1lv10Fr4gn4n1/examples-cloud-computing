package com.example.jpa.service

import com.example.jpa.repository.Customer
import com.example.jpa.repository.CustomerRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val repository: CustomerRepository) : CustomerService {

    override fun addCustomer(customer: Customer) {
        repository.saveAndFlush(customer)
    }

    override fun getAllCustomers(): List<Customer> {
        val sort = Sort.by(Sort.Direction.ASC, "lastName")
        return repository.findAll(sort).toList()
    }
}