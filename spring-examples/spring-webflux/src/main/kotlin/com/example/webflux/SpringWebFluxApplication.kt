package com.example.webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebFluxApplication

// https://spring.io/guides/gs/reactive-rest-service/
fun main(args: Array<String>) {
	runApplication<SpringWebFluxApplication>(*args)
}
