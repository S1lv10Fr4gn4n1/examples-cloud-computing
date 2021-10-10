package com.sfs.springtdd.service

import com.sfs.springtdd.domain.Car

interface CarService {

    fun getCarDetails(name: String): Car
}