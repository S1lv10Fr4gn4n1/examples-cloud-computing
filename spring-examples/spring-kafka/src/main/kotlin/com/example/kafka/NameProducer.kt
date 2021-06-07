package com.example.kafka

import java.util.logging.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NameProducer {

    private val logger = Logger.getLogger(NameProducer::class.simpleName)

    @Scheduled(fixedRate = 1000)
    fun produceIncreaseName() {
        logger.info("Producing increase name event")
    }

    @Scheduled(fixedRate = 1000)
    fun produceDecreaseName() {
        logger.info("Producing decrease name event")
    }
}