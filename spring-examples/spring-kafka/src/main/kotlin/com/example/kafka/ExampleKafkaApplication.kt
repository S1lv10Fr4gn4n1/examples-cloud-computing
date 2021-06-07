package com.example.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

// https://dzone.com/articles/introduction-to-apache-kafka-with-spring
@SpringBootApplication
@EnableScheduling
class ExampleKafkaApplication

fun main(args: Array<String>) {
	runApplication<ExampleKafkaApplication>(*args)
}
