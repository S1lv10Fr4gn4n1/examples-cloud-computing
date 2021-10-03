package com.sfs.user.service.repository.model

import com.sfs.user.service.utils.NoArgConstructor
import org.springframework.data.redis.core.index.Indexed

@NoArgConstructor
data class UserMemory(
    val id: String,
    @Indexed val name: String,
    val email: String
)