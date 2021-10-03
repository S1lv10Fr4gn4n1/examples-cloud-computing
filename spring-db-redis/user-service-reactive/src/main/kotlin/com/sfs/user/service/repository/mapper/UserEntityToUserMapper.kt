package com.sfs.user.service.repository.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.service.model.User
import com.sfs.user.service.repository.model.UserEntity
import org.springframework.stereotype.Component

@Component
class UserEntityToUserMapper : Mapper<UserEntity, User> {

    override fun apply(item: UserEntity) =
        User(
            id = item.id,
            name = item.name,
            email = item.email
        )
}