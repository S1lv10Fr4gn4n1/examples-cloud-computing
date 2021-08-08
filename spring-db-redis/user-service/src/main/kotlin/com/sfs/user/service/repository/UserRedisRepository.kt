package com.sfs.user.service.repository

import com.sfs.user.service.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRedisRepository : UserMemoryRepository {

    override fun getUser(): User {
        TODO("Not yet implemented")

//            val key = "users"
//            redisTemplate
//                .opsForList()
//                .leftPushAll(key, "Silvio", "Isabela", "Sophie")
//
//            val value = redisTemplate.opsForList().leftPop(key)
//            LoggerFactory.getLogger("Test").info("Value found on redis $value")
    }
}