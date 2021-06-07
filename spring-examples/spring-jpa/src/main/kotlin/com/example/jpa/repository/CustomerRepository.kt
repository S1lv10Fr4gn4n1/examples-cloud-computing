package com.example.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByLastName(lastName: String): List<Customer>

//    fun findById(id: Long): Customer
}