package com.sfs.user.service.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.annotation.Id

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
data class User @JsonCreator constructor(
    @JsonProperty("id") @Id val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String
)