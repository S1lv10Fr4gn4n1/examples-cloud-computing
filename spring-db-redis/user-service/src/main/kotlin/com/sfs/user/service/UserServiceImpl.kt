package com.sfs.user.service

import com.sfs.user.service.UserServiceImpl.Companion.USER_CACHE
import com.sfs.user.service.mapper.UserEntityMapper
import com.sfs.user.service.mapper.UserMapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.UserDatabaseRepository
import com.sfs.user.service.repository.UserMemoryRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = [USER_CACHE])
class UserServiceImpl(
    private val userDatabaseRepository: UserDatabaseRepository,
//    private val userMemoryRepository: UserMemoryRepository,
    private val userMapper: UserMapper,
    private val userEntityMapper: UserEntityMapper
) : UserService {

    @Cacheable(cacheNames = [USERS])
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

    @Cacheable(cacheNames = [USERS])
    override fun searchByName(name: String, page: Int, size: Int): List<User> {
        val entities = userDatabaseRepository.findByNameContainsIgnoreCase(name)
        return entities.map(userMapper::apply)
    }

    @CacheEvict(cacheNames = [USERS], allEntries = true)
    override fun add(user: User) {
        val entity = userEntityMapper.apply(user)
        userDatabaseRepository.save(entity)
    }

    @CacheEvict(cacheNames = [USERS], allEntries = true)
    override fun update(user: User) {
        val optional = userDatabaseRepository.findById(user.id)
        if (!optional.isPresent) {
            return
        }

        val entity = userEntityMapper.apply(user)
        userDatabaseRepository.save(entity)
    }

    // TODO SFS test it, it shouldn't delete all entries
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