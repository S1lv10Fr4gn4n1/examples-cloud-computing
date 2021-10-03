package com.sfs.user.route.handler

import com.sfs.user.route.Constants
import com.sfs.user.route.handler.mapper.NotFoundRespMapper
import com.sfs.user.route.handler.mapper.OkRespMapper
import com.sfs.user.service.UserService
import com.sfs.user.util.logger.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetUserByIdHandler(
    private val okRespMapper: OkRespMapper,
    private val notFoundRespMapper: NotFoundRespMapper,
    private val logger: Logger,
    private val userService: UserService
) : BaseHandler {

    override fun handle(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable(Constants.FIELD_ID)
        return userService.getUser(id)
            .flatMap(okRespMapper::apply)
            .doOnError { logger.error(TAG, it) }
            .onErrorResume(notFoundRespMapper::apply)
    }

    companion object {

        private const val TAG = "GetUserByIdHandler"
    }
}