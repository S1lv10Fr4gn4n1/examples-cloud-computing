package com.sfs.user.service.repository

import java.io.Serializable
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration::class)
@EnableCaching
class RedisConfiguration {

    @Value("\${redis.hostName}")
    private lateinit var redisHostName: String

    @Value("\${redis.port}")
    private lateinit var redisPort: String

    @Value("\${redis.password}")
    private lateinit var redisPassword: String

    @Bean
    fun lettuceConnectionFactory() = LettuceConnectionFactory(
        RedisStandaloneConfiguration().apply {
            hostName = redisHostName
            port = redisPort.toInt()
            password = RedisPassword.of(redisPassword)
        }
    )

    @Bean
    fun redisTemplate(connectionFactory: LettuceConnectionFactory) =
        RedisTemplate<String, Serializable>().apply {
            setConnectionFactory(connectionFactory)
        }

    @Bean
    fun cacheManager(factory: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )

        return RedisCacheManager
            .builder(factory)
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }
}