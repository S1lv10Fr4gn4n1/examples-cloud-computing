package com.sfs.user.service

import com.sfs.user.service.UserServiceImpl.Companion.USER_CACHE
import com.sfs.user.service.exception.UserNotFound
import com.sfs.user.service.id.IdGenerator
import com.sfs.user.service.mapper.UserEntityMapper
import com.sfs.user.service.mapper.UserMapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.UserDatabaseRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = [USER_CACHE])
class UserServiceImpl(
    private val idGenerator: IdGenerator,
    private val userDatabaseRepository: UserDatabaseRepository,
    private val userEntityMapper: UserEntityMapper,
    private val userMapper: UserMapper
) : UserService {

    @Cacheable(cacheNames = [USERS], key = "'all_users_' + #page + '_' + #size")
    override fun getAllUsers(page: Int, size: Int): List<User> {
        val pageRequest = PageRequest.of(page, size)
        val entities = userDatabaseRepository.findAll(pageRequest)
        return entities.content.map(userMapper::apply)
    }

    @Cacheable(cacheNames = [USER], key = "#id", unless = "#result == null")
    override fun getUser(id: String): User {
        val entity = userDatabaseRepository.findById(id)
        return userMapper.apply(entity.get())
    }

    @Cacheable(cacheNames = [USERS], key = "'search_users_' + #name + '_' + #page + '_' + #size")
    override fun searchByName(name: String, page: Int, size: Int): List<User> {
        val entities = userDatabaseRepository.findByNameContainsIgnoreCase(name)
        return entities.map(userMapper::apply)
    }

    // not the best eviction strategy, but good for now
    @Caching(
        evict = [CacheEvict(cacheNames = [USER], key = "#id"), CacheEvict(cacheNames = [USERS], allEntries = true)]
    )
    override fun add(user: User): User {
        val id = idGenerator.getId(user.email)
        val entity = userEntityMapper.apply(user)
        val updatedEntity = userDatabaseRepository.save(entity.copy(id = id))
        return userMapper.apply(updatedEntity)
    }

    // not the best eviction strategy, but good for now
    @Caching(
        evict = [CacheEvict(cacheNames = [USER], key = "#id"), CacheEvict(cacheNames = [USERS], allEntries = true)]
    )
    override fun update(id: String, user: User): User {
        val optional = userDatabaseRepository.findById(id)
        if (!optional.isPresent) {
            throw UserNotFound()
        }

        val entity = optional.get()
        val updatedEntity = userDatabaseRepository.save(
            entity.copy(
                name = user.name,
                email = user.email
            )
        )
        return userMapper.apply(updatedEntity)
    }

    // not the best eviction strategy, but good for now
    @Caching(
        evict = [CacheEvict(cacheNames = [USER], key = "#id"), CacheEvict(cacheNames = [USERS], allEntries = true)]
    )
    override fun delete(id: String) {
        userDatabaseRepository.deleteById(id)
    }

    companion object {

        internal const val USER_CACHE = "userCache"
        internal const val USER = "user"
        internal const val USERS = "users"
    }
}