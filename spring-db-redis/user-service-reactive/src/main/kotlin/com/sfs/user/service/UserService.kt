package com.sfs.user.service

import com.sfs.user.service.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {

    fun getAllUsers(page: Int, size: Int): Flux<User>

    fun getUser(id: String): Mono<User>

    fun searchByName(name: String, page: Int, size: Int): Flux<User>

    fun add(user: User): Mono<User>

    fun update(user: User): Mono<User>

    fun delete(id: String): Mono<Boolean>
}