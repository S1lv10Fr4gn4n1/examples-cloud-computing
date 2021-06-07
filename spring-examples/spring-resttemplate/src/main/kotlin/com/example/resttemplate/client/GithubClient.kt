package com.example.resttemplate.client

import com.example.resttemplate.client.model.GithubRepositoryModel
import java.util.logging.Logger
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GithubClient(restTemplateBuilder: RestTemplateBuilder) {

    private val logger = Logger.getLogger(GithubClient::class.simpleName)
    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    fun getRepositoriesForUser(userName: String): List<GithubRepositoryModel> {
        val response =
            restTemplate.getForEntity(REPOSITORY_ENDPOINT, arrayOf<GithubRepositoryModel>()::class.java, userName)

        logger.info("request limit for github api ${response.headers["x-ratelimit-remaining"]}")

        return response.body?.toList() ?: emptyList()
    }

    companion object {
        private const val REPOSITORY_ENDPOINT = "https://api.github.com/users/{userName}/repos"
    }
}