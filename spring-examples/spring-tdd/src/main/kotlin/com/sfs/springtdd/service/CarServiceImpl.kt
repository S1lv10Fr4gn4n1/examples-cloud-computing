package com.sfs.springtdd.service

import com.sfs.springtdd.CarNotFoundException
import com.sfs.springtdd.domain.Car
import com.sfs.springtdd.service.repository.CarRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CarServiceImpl(private val carRepository: CarRepository) : CarService {

    @Cacheable(cacheNames = ["car"], key = "#name", unless = "#result == null")
    override fun getCarDetails(name: String): Car {
        return carRepository.findByName(name) ?: throw CarNotFoundException()
    }
}