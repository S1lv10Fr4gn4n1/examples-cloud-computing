package com.example.jpa.service

import com.example.jpa.repository.Customer

interface CustomerService {

    fun addCustomer(customer: Customer)

    fun getAllCustomers(): List<Customer>
}