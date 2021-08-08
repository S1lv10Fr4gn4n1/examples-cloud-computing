package com.sfs.user.service.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.model.UserEntity
import org.springframework.stereotype.Component

@Component
class UserEntityMapper : Mapper<User, UserEntity> {

    override fun apply(item: User): UserEntity {
        return UserEntity(
            id = item.id,
            name = item.name,
            email = item.email
        )
    }
}