package com.example.circuitbreaker

import org.slf4j.LoggerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(
    private val circuitBreakerFactory: CircuitBreakerFactory<Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration, Resilience4JConfigBuilder>,
    private val httpBinService: HttpBinService
) {

    private val logger = LoggerFactory.getLogger(DemoController::class.java)

    @GetMapping("/get")
    fun get(): Map<*, *> {
        return httpBinService.get()
    }

    @GetMapping("/delay/{seconds}")
    fun delay(@PathVariable seconds: Int): Map<*, *> {
        return circuitBreakerFactory.create("delay").run(httpBinService.delaySupplier(seconds)) {
            logger.warn("delay call failed error", it)
            mapOf("hello" to "world")
        }
    }
}