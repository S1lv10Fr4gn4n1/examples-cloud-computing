package com.example.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByLastName(lastName: String): Customer

    @Query("SELECT c FROM ${Customer.TABLE_NAME} c WHERE c.lastName LIKE CONCAT('%', :param, '%')")
    fun findAllCustomerLikeQuery(@Param("param") lastName: String): List<Customer>

    fun findByLastNameContaining(lastName: String): List<Customer>
}