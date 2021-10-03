package com.sfs.user.service.repository.datasource

import com.sfs.user.service.repository.model.UserMemory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserMemoryRepository {

    fun getAllUsers(page: Int, size: Int): Flux<UserMemory>

    fun saveAllUsers(page: Int, size: Int, users: List<UserMemory>): Mono<Long>

    fun getUser(id: String): Mono<UserMemory>

    fun saveUser(user: UserMemory): Mono<Boolean>

    fun findByName(name: String, page: Int, size: Int): Flux<UserMemory>

    fun saveSearchByName(name: String, page: Int, size: Int, users: List<UserMemory>): Mono<Long>

    fun update(user: UserMemory): Mono<Boolean>

    fun deleteById(id: String): Mono<Boolean>
}