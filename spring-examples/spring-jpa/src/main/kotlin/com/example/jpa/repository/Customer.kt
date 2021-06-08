package com.example.jpa.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = Customer.TABLE_NAME)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = FIELD_FIRST_NAME)
    val firstName: String,
    @Column(name = FIELD_LAST_NAME)
    val lastName: String,
    @OneToMany(fetch = FetchType.EAGER)
    val addresses: List<Address>
) {
    companion object {
        internal const val TABLE_NAME = "customer"
        private const val FIELD_FIRST_NAME = "first_name"
        private const val FIELD_LAST_NAME = "last_name"
    }
}