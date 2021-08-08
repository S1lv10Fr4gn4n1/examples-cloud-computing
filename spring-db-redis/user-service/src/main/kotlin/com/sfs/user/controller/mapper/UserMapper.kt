package com.sfs.user.controller.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.controller.model.NewUserRequest
import com.sfs.user.service.model.User

class UserMapper : Mapper<NewUserRequest, User> {

    override fun apply(item: NewUserRequest): User {
        return User(
            id = "",
            name = item.name,
            email = item.email
        )
    }
}