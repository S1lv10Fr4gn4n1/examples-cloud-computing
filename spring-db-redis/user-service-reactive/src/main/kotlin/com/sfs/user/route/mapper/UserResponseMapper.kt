package com.sfs.user.route.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.route.model.UserResponse
import com.sfs.user.service.model.User
import org.springframework.stereotype.Component

@Component
class UserResponseMapper : Mapper<User, UserResponse> {

    override fun apply(item: User) =
        UserResponse(
            id = item.id,
            name = item.name,
            email = item.email
        )
}