package com.sfs.springtdd.controller

import com.sfs.springtdd.CarNotFoundException
import com.sfs.springtdd.domain.Car
import com.sfs.springtdd.service.CarService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(CarController::class)
class CarControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var carService: CarService

    @Test
    fun `Given get car Then returns car details`() {
        given(carService.getCarDetails(anyString())).willReturn(Car(name = CAR_NAME, type = CAR_TYPE))

        mockMvc.perform(get("/cars/$CAR_NAME"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("name").value(CAR_NAME))
            .andExpect(jsonPath("type").value(CAR_TYPE))
    }

    @Test
    fun `Given get car Then returns card not found exception`() {
        given(carService.getCarDetails(anyString())).willThrow(CarNotFoundException())

        mockMvc.perform(get("/cars/xxx"))
            .andExpect(status().isNotFound)
    }

    companion object {

        private const val CAR_NAME = "prius"
        private const val CAR_TYPE = "hybrid"
    }
}