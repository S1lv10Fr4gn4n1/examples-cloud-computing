package com.sfs.springtdd.controller

import com.sfs.springtdd.CarNotFoundException
import com.sfs.springtdd.domain.Car
import com.sfs.springtdd.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cars")
class CarController(private val cardService: CarService) {

    @GetMapping("/{name}")
    fun getCar(@PathVariable name: String): Car {
        return cardService.getCarDetails(name)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun carNotFoundHandler(exception: CarNotFoundException) {
    }
}
