package com.sfs.springtdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SpringTddApplication

fun main(args: Array<String>) {
    runApplication<SpringTddApplication>(*args)
}
