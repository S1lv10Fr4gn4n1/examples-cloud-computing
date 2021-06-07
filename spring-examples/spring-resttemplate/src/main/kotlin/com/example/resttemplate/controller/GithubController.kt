package com.example.resttemplate.controller

import com.example.resttemplate.client.GithubClient
import com.example.resttemplate.controller.model.RepositoryUiModel
import com.example.resttemplate.mapper.GithubModelToUiModel
import java.util.logging.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/github")
class GithubController(
    private val githubClient: GithubClient,
    private val mapper: GithubModelToUiModel
) {

    private val logger = Logger.getLogger(GithubController::class.simpleName)

    @GetMapping("/repos/{userName}")
    @ResponseBody
    fun getReposFromUserName(@PathVariable userName: String): ResponseEntity<List<RepositoryUiModel>> {
        logger.info("requesting repositories for user $userName")
        val githubModels = githubClient.getRepositoriesForUser(userName)
        val repositoryUiModels = githubModels.map { mapper.toUiModel(it) }

        logger.info("repositories for user $userName $repositoryUiModels")
        return ResponseEntity.ok()
            .body(repositoryUiModels)
    }
}