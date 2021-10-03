package com.sfs.user.service.repository.datasource

import com.sfs.user.service.repository.model.UserMemory
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRedisRepository(
    private val redisTemplate: ReactiveRedisTemplate<String, UserMemory>
) : UserMemoryRepository {

    override fun getAllUsers(page: Int, size: Int): Flux<UserMemory> {
        return redisTemplate
            .opsForList()
            .range(getUsersKey(page, size), 0, size.toLong())
    }

    override fun saveAllUsers(page: Int, size: Int, users: List<UserMemory>): Mono<Long> {
        return redisTemplate
            .opsForList()
            .leftPushAll(getUsersKey(page, size), users)
    }

    override fun getUser(id: String): Mono<UserMemory> {
        return redisTemplate
            .opsForValue()
            .get(getUserKey(id))
    }

    override fun saveUser(user: UserMemory): Mono<Boolean> {
        return redisTemplate
            .opsForValue()
            .set(getUserKey(user.id), user)
    }

    override fun findByName(name: String, page: Int, size: Int): Flux<UserMemory> {
        return redisTemplate
            .opsForList()
            .range(getUsersKey(name, page, size), 0, size.toLong())
    }

    override fun saveSearchByName(name: String, page: Int, size: Int, users: List<UserMemory>): Mono<Long> {
        return redisTemplate
            .opsForList()
            .leftPushAll(getUsersKey(name, page, size), users)
    }

    override fun update(user: UserMemory): Mono<Boolean> {
        return redisTemplate
            .hasKey(getUserKey(user.id))
            .flatMap { hasKey -> if (hasKey) saveUser(user) else Mono.empty() }
    }

    override fun deleteById(id: String): Mono<Boolean> {
        // TODO SFS, when delete an user, it might need to update the other cached keys with lists
        //  - check if there is a way to find out in each keys it's available so we don't need to evict all list caches
        return redisTemplate
            .opsForValue()
            .delete(getUserKey(id))
    }

    private fun getUsersKey(page: Int, size: Int): String {
        return USERS.plus("-p:{$page}-s:{$size}")
    }

    private fun getUsersKey(name: String, page: Int, size: Int): String {
        return USERS.plus("-n:${name}-p:{$page}-s:{$size}")
    }

    private fun getUserKey(id: String): String {
        return USER.plus("_$id")
    }

    companion object {

        internal const val USER = "user"
        internal const val USERS = "users"
    }
}