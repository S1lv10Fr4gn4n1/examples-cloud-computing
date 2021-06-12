package com.example.mongodb.repository

import org.springframework.data.mongodb.repository.MongoRepository

interface CustomerRepository : MongoRepository<Customer, String> {

    fun findByLastName(lastName: String): List<Customer>
}