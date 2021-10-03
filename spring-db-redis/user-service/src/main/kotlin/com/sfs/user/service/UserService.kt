package com.sfs.user.service

import com.sfs.user.service.model.User

interface UserService {

    fun getAllUsers(page: Int, size: Int): List<User>

    fun getUser(id: String): User

    fun searchByName(name: String, page: Int, size: Int): List<User>

    fun add(user: User): User

    fun update(id: String, user: User): User

    fun delete(id: String)
}