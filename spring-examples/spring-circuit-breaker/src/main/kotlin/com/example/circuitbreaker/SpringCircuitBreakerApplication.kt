package com.example.circuitbreaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringCircuitBreakerApplication

fun main(args: Array<String>) {
	runApplication<SpringCircuitBreakerApplication>(*args)
}
