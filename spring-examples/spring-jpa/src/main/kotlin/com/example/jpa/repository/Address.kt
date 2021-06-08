package com.example.jpa.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = Address.TABLE_NAME)
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = FIELD_STREET)
    val street: String,
    @Column(name = FIELD_NUMBER)
    val number: String,
    @Column(name = FIELD_CITY)
    val city: String,
    @Column(name = FIELD_ZIP_CODE)
    val zipCode: String
) {
    companion object {
        internal const val TABLE_NAME = "address"
        private const val FIELD_STREET = "street"
        private const val FIELD_NUMBER = "number"
        private const val FIELD_CITY = "city"
        private const val FIELD_ZIP_CODE = "zip_code"
    }
}