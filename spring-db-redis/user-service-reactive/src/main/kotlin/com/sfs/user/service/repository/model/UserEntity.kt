package com.sfs.user.service.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table(UserEntity.TABLE)
data class UserEntity(
    @Id private val id: String,
    val name: String,
    val email: String
) : Persistable<String> {

    @Transient
    private var isNew = false

    override fun isNew() = isNew

    override fun getId() = id

    fun markNew() {
        isNew = true
    }

    companion object {

        internal const val TABLE = "user2"
    }
}