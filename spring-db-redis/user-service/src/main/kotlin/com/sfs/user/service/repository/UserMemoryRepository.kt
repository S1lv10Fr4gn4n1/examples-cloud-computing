package com.sfs.user.service.repository

import com.sfs.user.service.model.User

// TODO SFS implement redis cache
interface UserMemoryRepository {

    fun getUser(): User
}