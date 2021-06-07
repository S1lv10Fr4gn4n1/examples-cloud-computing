package com.example.resttemplate.client.model

import com.fasterxml.jackson.annotation.JsonProperty

class GithubRepositoryModel(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("html_url")
    val url: String,
    @JsonProperty("fork")
    val isForked: Boolean,
    @JsonProperty("created_at")
    val createAt: String, // 2021-05-02T17:54:23Z
    @JsonProperty("updated_at")
    val updateAt: String
)