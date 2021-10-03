package com.sfs.user.route.mapper

import com.sfs.user.base.Mapper
import com.sfs.user.route.model.UserRequest
import com.sfs.user.service.model.User
import org.springframework.stereotype.Component

@Component
class UserRequestToUseMapper : Mapper<UserRequest, User> {

    override fun apply(item: UserRequest) =
        User(
            id = "",
            name = item.name,
            email = item.email
        )
}