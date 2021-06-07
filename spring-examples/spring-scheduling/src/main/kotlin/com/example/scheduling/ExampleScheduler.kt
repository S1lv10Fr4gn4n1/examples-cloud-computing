package com.example.scheduling

import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ExampleScheduler {

    private val logger = Logger.getLogger(ExampleScheduler::class.simpleName)

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    @Scheduled(fixedRate = 1000)
    fun reportCurrentDateTime() {
        val formattedtDateTime = dateFormat.format(Date())
        logger.info("Reporting time $formattedtDateTime")
    }
}