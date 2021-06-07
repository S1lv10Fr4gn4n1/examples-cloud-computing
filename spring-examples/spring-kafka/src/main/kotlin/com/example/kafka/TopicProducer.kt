package com.example.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class TopicProducer(
    @Value("\${kafka.topic.name_increase}")
    private val topicIncrease: String,
    @Value("\${kafka.topic.name_decrease}")
    private val topicDecrease: String,
    @Value("\${kafka.config.partitions}")
    private val replicas: Int,
    @Value("\${kafka.config.partitions}")
    private val partitions: Int
) {
    @Bean
    fun increase(): NewTopic {
        return TopicBuilder.name(topicIncrease)
            .partitions(partitions)
            .replicas(replicas)
            .build()
    }

    @Bean
    fun decrease(): NewTopic {
        return TopicBuilder.name(topicDecrease)
            .partitions(partitions)
            .replicas(replicas)
            .build()
    }
}