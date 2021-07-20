package com.example.webflux

import java.time.Duration
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class WebFluxFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return Mono
            .delay(Duration.ofMillis(200))
            .then(chain.filter(exchange))
    }
}