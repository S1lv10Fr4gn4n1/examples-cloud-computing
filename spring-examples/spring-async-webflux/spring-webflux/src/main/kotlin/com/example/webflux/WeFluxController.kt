package com.example.webflux

import java.time.Duration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class WeFluxController {

    @GetMapping("/flux_result")
    fun getResult(): Mono<String> {
        return Mono.defer {
            Mono.just("Result is ready")
                .delaySubscription(Duration.ofMillis(500))
        }
    }
}