package com.sfs.springtdd.service

import com.sfs.springtdd.CarNotFoundException
import com.sfs.springtdd.domain.Car
import com.sfs.springtdd.service.repository.CarRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CarServiceTest {

    @Mock
    private lateinit var carRepository: CarRepository

    private lateinit var carService: CarService

    @BeforeEach
    fun setUp() {
        carService = CarServiceImpl(carRepository)
    }

    @Test
    fun `findByName Given car name Then returns Car object`() {
        given(carRepository.findByName(CAR_NAME)).willReturn(Car(name = CAR_NAME, type = CAR_TYPE))

        val carResult = carService.getCarDetails(CAR_NAME)

        assertThat(carResult.name).isEqualTo(CAR_NAME)
        assertThat(carResult.type).isEqualTo(CAR_TYPE)
    }

    @Test
    fun `findByName Given invalid car name Then throws CarNotFoundException`() {
        given(carRepository.findByName(CAR_NAME)).willReturn(null)

        assertThrows<CarNotFoundException> {
            carService.getCarDetails(CAR_NAME)
        }
    }

    companion object {

        private const val CAR_NAME = "prius"
        private const val CAR_TYPE = "hybrid"
    }
}