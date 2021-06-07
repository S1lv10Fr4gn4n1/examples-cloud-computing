package com.example.resttemplate.mapper

import com.example.resttemplate.client.model.GithubRepositoryModel
import com.example.resttemplate.controller.model.RepositoryUiModel
import org.springframework.stereotype.Component

@Component
class GithubModelToUiModel {

    fun toUiModel(model: GithubRepositoryModel): RepositoryUiModel {
        return RepositoryUiModel(
            name = model.name,
            url = model.url,
            isForked = model.isForked,
            createdAt = model.createAt,
            updatedAt = model.updateAt
        )
    }
}