package com.example.jpa.service

import com.example.jpa.repository.Address
import com.example.jpa.repository.Customer

interface CustomerService {

    fun addCustomer(customer: Customer)

    fun getAllCustomers(): List<Customer>

    fun getCustomerByLastName(lastName: String): Customer

    fun findAllCustomerLikeQuery(lastName: String): List<Customer>

    fun findByLastNameContaining(lastName: String): List<Customer>

    fun getCustomersPaginated(first: Int, max: Int): List<Customer>

    fun addAddress(address: Address)
}