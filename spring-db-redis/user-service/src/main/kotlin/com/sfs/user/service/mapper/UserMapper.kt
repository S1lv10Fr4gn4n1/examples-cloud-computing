package com.sfs.user.service.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.model.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper : Mapper<UserEntity, User> {

    override fun apply(item: UserEntity): User {
        return User(
            id = item.id,
            name = item.name,
            email = item.email
        )
    }
}