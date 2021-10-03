package com.sfs.user.service.repository

import com.sfs.user.service.exception.UserNotFound
import com.sfs.user.service.id.IdGenerator
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.datasource.UserDatabaseRepository
import com.sfs.user.service.repository.datasource.UserMemoryRepository
import com.sfs.user.service.repository.mapper.UserEntityToUserMapper
import com.sfs.user.service.repository.mapper.UserEntityToUserMemoryMapper
import com.sfs.user.service.repository.mapper.UserMemoryToUserMapper
import com.sfs.user.service.repository.mapper.UserToEntityMapper
import com.sfs.user.service.repository.mapper.UserToMemoryMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class UserRepositoryImpl(
    private val idGenerator: IdGenerator,
    private val userDatabaseRepository: UserDatabaseRepository,
    private val userMemoryRepository: UserMemoryRepository,
    // mappers
    private val userToEntityMapper: UserToEntityMapper,
    private val entityToUserMapper: UserEntityToUserMapper,
    private val entityToMemoryMapper: UserEntityToUserMemoryMapper,
    private val memoryToUserMapper: UserMemoryToUserMapper,
    private val userToMemoryMapper: UserToMemoryMapper
) : UserRepository {

    override fun getAllUsers(page: Int, size: Int): Flux<User> {
        return userMemoryRepository.getAllUsers(page, size)
            .switchIfEmpty(
                userDatabaseRepository.findBy(PageRequest.of(page, size))
                    .map(entityToMemoryMapper::apply)
                    .collectList()
                    .flatMapMany { users ->
                        userMemoryRepository.saveAllUsers(page, size, users)
                            .thenReturn(users)
                            .flatMapMany { Flux.fromIterable(it) }
                    }
            )
            .map(memoryToUserMapper::apply)

    }

    override fun getUser(id: String): Mono<User> {
        return userMemoryRepository.getUser(id)
            .switchIfEmpty {
                userDatabaseRepository.findById(id)
                    .switchIfEmpty(Mono.error(UserNotFound()))
                    .map(entityToMemoryMapper::apply)
                    .flatMap { userMemoryRepository.saveUser(it).thenReturn(it) }
            }
            .map(memoryToUserMapper::apply)
    }

    override fun searchByName(name: String, page: Int, size: Int): Flux<User> {
        return userMemoryRepository.findByName(name, page, size)
            .switchIfEmpty(
                userDatabaseRepository.findByNameQuery(name, page, size)
                    .map(entityToMemoryMapper::apply)
                    .collectList()
                    .flatMapMany { users ->
                        userMemoryRepository.saveSearchByName(name, page, size, users)
                            .thenReturn(users)
                            .flatMapMany { Flux.fromIterable(it) }
                    }
            )
            .map(memoryToUserMapper::apply)
    }

    override fun add(user: User): Mono<User> {
        return Mono.fromCallable {
            val id = idGenerator.getId(user.email)
            userToEntityMapper.apply(user.copy(id = id))
        }.flatMap {
            val entity = it.also { it.markNew() }
            userDatabaseRepository.save(entity)
                .map(entityToUserMapper::apply)
        }
    }

    override fun update(user: User): Mono<User> {
        return userDatabaseRepository.findById(user.id)
            .switchIfEmpty(Mono.error(UserNotFound()))
            .map { it.copy(name = user.name, email = user.email) }
            .flatMap {
                userDatabaseRepository.save(it)
                    .then(userMemoryRepository.update(entityToMemoryMapper.apply(it)))
                    .thenReturn(entityToUserMapper.apply(it))
            }
    }

    override fun delete(id: String): Mono<Boolean> {
        return userDatabaseRepository.deleteById(id)
            .then(userMemoryRepository.deleteById(id))
    }
}