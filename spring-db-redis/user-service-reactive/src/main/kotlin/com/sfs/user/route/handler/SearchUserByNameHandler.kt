package com.sfs.user.route.handler

import com.sfs.user.route.Constants
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
class SearchUserByNameHandler(
    private val logger: Logger,
    private val userService: UserService,
    private val userResponseMapper: UserResponseMapper
) : BaseHandler {

    override fun handle(request: ServerRequest): Mono<ServerResponse> {
        val (page, size) = request.getPageAndSize()
        val name = request.pathVariable(Constants.FIELD_NAME)

        val allUsersFlux = userService.searchByName(name, page, size)
            .map(userResponseMapper::apply)
            .doOnError { logger.error(TAG, it) }

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(allUsersFlux, UserResponse::class.java)
    }

    companion object {

        private const val TAG = "SearchUserByNameHandler"
    }
}