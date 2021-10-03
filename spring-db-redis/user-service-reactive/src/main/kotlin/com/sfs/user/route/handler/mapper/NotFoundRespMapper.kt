package com.sfs.user.route.handler.mapper

import com.sfs.user.base.Mapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class NotFoundRespMapper : Mapper<Throwable, Mono<ServerResponse>> {

    override fun apply(item: Throwable) = ServerResponse.notFound().build()
}