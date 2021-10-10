package com.sfs.springtdd.service.repository

import com.sfs.springtdd.domain.Car
import org.springframework.data.repository.CrudRepository

interface CarRepository : CrudRepository<Car, String> {

    fun findByName(name: String): Car?
}
