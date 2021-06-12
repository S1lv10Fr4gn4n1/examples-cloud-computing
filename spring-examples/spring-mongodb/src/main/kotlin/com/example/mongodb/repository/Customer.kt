package com.example.mongodb.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(Customer.DOCUMENT_NAME)
data class Customer(
    @Id
    val id: String = "",
    val firstName: String,
    val lastName: String
) {
    companion object {
        internal const val DOCUMENT_NAME = "customers"
    }
}