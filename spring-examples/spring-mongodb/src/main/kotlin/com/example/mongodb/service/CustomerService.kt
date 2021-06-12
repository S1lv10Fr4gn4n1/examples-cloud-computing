package com.example.mongodb.service

import com.example.mongodb.repository.Customer

interface CustomerService {

    fun addCustomer(customer: Customer)

    fun getAllCustomers(): List<Customer>

    fun getByLastName(lastName: String): List<Customer>
}