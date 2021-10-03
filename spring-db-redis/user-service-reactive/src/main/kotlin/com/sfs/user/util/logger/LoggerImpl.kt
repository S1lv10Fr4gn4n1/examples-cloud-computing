package com.sfs.user.util.logger

import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.stereotype.Component

@Component
class LoggerImpl : Logger {

    private val logger = LoggerFactory.getLogger("App")

    override fun info(tag: String, message: String) {
        logger.info(MarkerFactory.getMarker(tag), message)
    }

    override fun error(tag: String, throwable: Throwable) {
        logger.error(MarkerFactory.getMarker(tag), "", throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable) {
        logger.error(MarkerFactory.getMarker(tag), message, throwable)
    }
}