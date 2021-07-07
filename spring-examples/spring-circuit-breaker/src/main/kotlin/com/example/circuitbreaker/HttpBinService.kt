package com.example.circuitbreaker

import java.util.function.Supplier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class HttpBinService(private val restTemplate: RestTemplate) {

    fun get(): Map<*, *> {
        return restTemplate.getForObject(HTTPBIN_URL + "get", Map::class)
    }

    fun delay(seconds: Int): Map<*, *> {
        return restTemplate.getForObject(HTTPBIN_URL + "delay/$seconds", Map::class)
    }

    fun delaySupplier(seconds: Int): Supplier<Map<*, *>> {
        return Supplier { this.delay(seconds) }
    }

    companion object {
        private const val HTTPBIN_URL = "https://httpbin.org/"
    }

}