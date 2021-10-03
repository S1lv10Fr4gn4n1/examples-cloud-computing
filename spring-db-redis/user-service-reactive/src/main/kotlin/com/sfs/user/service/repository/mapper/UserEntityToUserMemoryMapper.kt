package com.sfs.user.service.repository.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.service.repository.model.UserEntity
import com.sfs.user.service.repository.model.UserMemory
import org.springframework.stereotype.Component

@Component
class UserEntityToUserMemoryMapper : Mapper<UserEntity, UserMemory> {

    override fun apply(item: UserEntity) =
        UserMemory(
            id = item.id,
            name = item.name,
            email = item.email
        )
}