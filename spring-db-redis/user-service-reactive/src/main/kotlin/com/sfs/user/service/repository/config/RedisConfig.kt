package com.sfs.user.service.repository.config

import com.sfs.user.service.repository.model.UserMemory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Value("\${redis.hostName}")
    private lateinit var redisHostName: String

    @Value("\${redis.port}")
    private lateinit var redisPort: String

    @Value("\${redis.password}")
    private lateinit var redisPassword: String

    @Bean
    @Primary
    fun lettuceConnectionFactory(): ReactiveRedisConnectionFactory {
        val configuration = RedisStandaloneConfiguration().apply {
            hostName = redisHostName
            port = redisPort.toInt()
            password = RedisPassword.of(redisPassword)
        }
        return LettuceConnectionFactory(configuration)
    }

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, UserMemory> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(UserMemory::class.java)
        val builder = RedisSerializationContext.newSerializationContext<String, UserMemory>(keySerializer)
        val context = builder.value(valueSerializer).build()
        return ReactiveRedisTemplate(factory, context)
    }
}