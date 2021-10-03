package com.sfs.user.service.repository.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.model.UserMemory
import org.springframework.stereotype.Component

@Component
class UserToMemoryMapper : Mapper<User, UserMemory> {

    override fun apply(item: User) =
        UserMemory(
            id = item.id,
            name = item.name,
            email = item.email
        )
}