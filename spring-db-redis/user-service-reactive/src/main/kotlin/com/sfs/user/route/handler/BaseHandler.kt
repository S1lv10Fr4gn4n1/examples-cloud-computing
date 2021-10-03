package com.sfs.user.route.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface BaseHandler {

    fun handle(request: ServerRequest): Mono<ServerResponse>
}