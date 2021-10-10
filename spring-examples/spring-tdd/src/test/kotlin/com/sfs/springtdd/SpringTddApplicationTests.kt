package com.sfs.springtdd

import com.sfs.springtdd.domain.Car
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("dev")
class SpringTddApplicationTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `GET car Then returns OK status and card details`() {
        val response = restTemplate.getForEntity<Car>("/cars/$CAR_NAME", CAR_NAME)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.name).isEqualTo(CAR_NAME)
        assertThat(response.body?.type).isEqualTo(CAR_TYPE)
    }

    companion object {

        private const val CAR_NAME = "prius"
        private const val CAR_TYPE = "hybrid"
    }
}
