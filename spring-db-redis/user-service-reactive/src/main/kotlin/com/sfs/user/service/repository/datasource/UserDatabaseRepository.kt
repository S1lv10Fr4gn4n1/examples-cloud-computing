package com.sfs.user.service.repository.datasource

import com.sfs.user.service.repository.model.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserDatabaseRepository : ReactiveSortingRepository<UserEntity, String> {

    fun findBy(pageable: Pageable): Flux<UserEntity>
    
    @Query("select * from user1 WHERE UPPER(name) like UPPER(CONCAT('%', :name, '%')) LIMIT  :size OFFSET :page")
    fun findByNameQuery(name: String, page: Int, size: Int): Flux<UserEntity>
}