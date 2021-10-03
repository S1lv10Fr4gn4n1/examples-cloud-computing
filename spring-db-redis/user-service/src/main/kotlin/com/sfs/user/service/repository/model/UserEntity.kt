package com.sfs.user.service.repository.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = UserEntity.TABLE)
data class UserEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val email: String
) {

    companion object {

        internal const val TABLE = "user1"
    }
}