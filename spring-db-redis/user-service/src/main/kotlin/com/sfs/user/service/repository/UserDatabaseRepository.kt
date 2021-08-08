package com.sfs.user.service.repository

import com.sfs.user.service.repository.model.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDatabaseRepository : PagingAndSortingRepository<UserEntity, String> {

    fun findByNameContainsIgnoreCase(name: String): List<UserEntity>
}