package com.example.resttemplate.controller.model

data class RepositoryUiModel(
    val name: String,
    val url: String,
    val isForked: Boolean,
    val createdAt: String,
    val updatedAt: String
)