package com.sfs.user.route.handler

import com.sfs.user.route.mapper.UserResponseMapper
import com.sfs.user.route.model.UserResponse
import com.sfs.user.service.UserService
import com.sfs.user.util.logger.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class GetUsersHandler(
    private val logger: Logger,
    private val userService: UserService,
    private val userResponseMapper: UserResponseMapper
) : BaseHandler {

    override fun handle(request: ServerRequest): Mono<ServerResponse> {
        val (page, size) = request.getPageAndSize()

        val allUsersFlux = userService.getAllUsers(page, size)
            .map(userResponseMapper::apply)
//            .delayElements(Duration.ofMillis(100)) // to play with stream
            .doOnError { logger.error(TAG, it) }

        return ServerResponse
            .ok()
//            .contentType(MediaType.APPLICATION_STREAM_JSON) // to play with stream
            .contentType(MediaType.APPLICATION_JSON)
            .body(allUsersFlux, UserResponse::class.java)
    }

    companion object {

        private const val TAG = "GetUsersHandler"
    }
}