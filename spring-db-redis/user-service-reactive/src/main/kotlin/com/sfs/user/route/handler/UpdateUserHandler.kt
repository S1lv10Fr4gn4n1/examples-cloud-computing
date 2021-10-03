package com.sfs.user.route.handler

import com.sfs.user.route.Constants
import com.sfs.user.route.handler.mapper.NotFoundRespMapper
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
class UpdateUserHandler(
    private val logger: Logger,
    private val notFoundRespMapper: NotFoundRespMapper,
    private val okRespMapper: OkRespMapper,
    private val userService: UserService,
    private val userMapper: UserRequestToUseMapper
) : BaseHandler {

    override fun handle(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable(Constants.FIELD_ID)
        return request.bodyToMono<UserRequest>()
            .map { userMapper.apply(it).copy(id = id) }
            .flatMap(userService::update)
            .flatMap(okRespMapper::apply)
            .doOnError { logger.error(TAG, it) }
            .onErrorResume(notFoundRespMapper::apply)
    }

    companion object {

        private const val TAG = "UpdateUserHandler"
    }
}