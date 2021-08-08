package com.sfs.user.controller.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.controller.model.UserResponse
import com.sfs.user.service.model.User
import org.springframework.stereotype.Component

@Component
class UserResponseMapper : Mapper<User, UserResponse> {

    override fun apply(item: User): UserResponse {
        return UserResponse(
            id = item.id,
            name = item.name,
            email = item.email
        )
    }
}