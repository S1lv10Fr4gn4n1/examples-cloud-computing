package com.sfs.user.controller.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.controller.mapper.UserMapper.Companion.USER_MAPPER_CONTROLLER
import com.sfs.user.controller.model.UserRequest
import com.sfs.user.service.model.User
import org.springframework.stereotype.Component

@Component(USER_MAPPER_CONTROLLER)
class UserMapper : Mapper<UserRequest, User> {

    override fun apply(item: UserRequest): User {
        return User(
            id = "",
            name = item.name,
            email = item.email
        )
    }

    companion object {

        const val USER_MAPPER_CONTROLLER = "userMapperController"
    }
}