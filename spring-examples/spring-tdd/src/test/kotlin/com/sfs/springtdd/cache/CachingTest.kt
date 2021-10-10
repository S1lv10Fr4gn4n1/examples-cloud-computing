package com.sfs.springtdd.cache

import com.sfs.springtdd.domain.Car
import com.sfs.springtdd.service.CarService
import com.sfs.springtdd.service.repository.CarRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.BDDMockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
class CachingTest {

    @Autowired
    private lateinit var service: CarService

    @MockBean
    private lateinit var repository: CarRepository

    @Test
    fun `When calling getCarDetails twice Then it calls repository findByName just once`() {
        given(repository.findByName(anyString())).willReturn(Car(name = CAR_NAME, type = CAR_TYPE))

        service.getCarDetails(CAR_NAME)
        service.getCarDetails(CAR_NAME)

        verify(repository, times(1)).findByName(CAR_NAME)
    }

    companion object {

        private const val CAR_NAME = "prius"
        private const val CAR_TYPE = "hybrid"
    }
}