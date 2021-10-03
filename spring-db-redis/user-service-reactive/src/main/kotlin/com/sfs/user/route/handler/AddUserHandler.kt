package com.sfs.user.route.handler

import com.sfs.user.route.handler.mapper.OkRespMapper
import com.sfs.user.route.mapper.UserRequestToUseMapper
import com.sfs.user.route.model.UserRequest
import com.sfs.user.service.UserService
import com.sfs.user.util.logger.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class AddUserHandler(
    private val logger: Logger,
    private val okRespMapper: OkRespMapper,
    private val userService: UserService,
    private val userMapper: UserRequestToUseMapper
) : BaseHandler {

    override fun handle(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono<UserRequest>()
            .map(userMapper::apply)
            .flatMap(userService::add)
            .flatMap(okRespMapper::apply)
            .doOnError { logger.error(TAG, it) }
    }

    companion object {

        private const val TAG = "AddUserHandler"
    }
}