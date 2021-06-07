package com.example.kafka

import java.util.logging.Logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NameConsumer {

    private val logger = Logger.getLogger(NameConsumer::class.simpleName)

    @KafkaListener(id = "increment", topics = ["\${kafka.topic.name_increase}"])
    fun increase(name: String) {
        logger.info("Listing increase -> $name")
    }

    @KafkaListener(id = "decrement", topics = ["\${kafka.topic.name_decrease}"])
    fun decrease(name: String) {
        logger.info("Listing decrement -> $name")
    }
}