package com.sfs.springtdd.service.repository

import com.sfs.springtdd.domain.Car
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun `findByName Given car name Then returns car details`() {
        val savedCar = entityManager.persistAndFlush(Car(name = CAR_NAME, type = CAR_TYPE))
//        val savedCar = Car(name = CAR_NAME, type = CAR_TYPE)

        val carResult = carRepository.findByName(CAR_NAME)

        assertThat(carResult?.name).isEqualTo(savedCar.name)
        assertThat(carResult?.type).isEqualTo(savedCar.type)
    }

    companion object {

        private const val CAR_NAME = "fiat"
        private const val CAR_TYPE = "alcohol"
    }
}