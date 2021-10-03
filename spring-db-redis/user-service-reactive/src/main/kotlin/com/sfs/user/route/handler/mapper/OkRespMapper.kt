package com.sfs.user.route.handler.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.route.mapper.UserResponseMapper
import com.sfs.user.route.model.UserResponse
import com.sfs.user.service.model.User
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class OkRespMapper(private val userResponseMapper: UserResponseMapper) :
    Mapper<User, Mono<ServerResponse>> {

    override fun apply(item: User) =
        ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(item.toUserResponseMono(), UserResponse::class.java)

    private fun User.toUserResponseMono() =
        Mono.just(userResponseMapper.apply(this))
}