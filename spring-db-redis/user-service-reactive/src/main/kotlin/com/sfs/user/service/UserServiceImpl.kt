package com.sfs.user.service

import com.sfs.user.service.model.User
import com.sfs.user.service.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun getAllUsers(page: Int, size: Int): Flux<User> {
        return userRepository.getAllUsers(page, size)
    }

    override fun getUser(id: String): Mono<User> {
        return userRepository.getUser(id)
    }

    override fun searchByName(name: String, page: Int, size: Int): Flux<User> {
        return userRepository.searchByName(name, page, size)
    }

    override fun add(user: User): Mono<User> {
        return userRepository.add(user)
    }

    override fun update(user: User): Mono<User> {
        return userRepository.update(user)
    }

    override fun delete(id: String): Mono<Boolean> {
        return userRepository.delete(id)
    }
}