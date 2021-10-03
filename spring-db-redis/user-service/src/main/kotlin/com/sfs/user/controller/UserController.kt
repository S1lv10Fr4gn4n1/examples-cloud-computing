package com.sfs.user.controller

import com.sfs.user.controller.mapper.UserMapper
import com.sfs.user.controller.mapper.UserResponseMapper
import com.sfs.user.controller.model.UserRequest
import com.sfs.user.controller.model.UserResponse
import com.sfs.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val userResponseMapper: UserResponseMapper,
    @Qualifier(UserMapper.USER_MAPPER_CONTROLLER) private val userMapper: UserMapper
) {

    private val logger = LoggerFactory.getLogger(UserController::class.simpleName)

    @GetMapping
    fun getUsers(
        @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE) page: Int,
        @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE) size: Int
    ): ResponseEntity<List<UserResponse>> {
        return try {
            val userResponseList = userService.getAllUsers(page, size).map(userResponseMapper::apply)
            ResponseEntity.ok().body(userResponseList)
        } catch (e: Exception) {
            logger.error("getUsers", e)
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/{$FIELD_ID}")
    fun getUserById(@PathVariable(FIELD_ID) id: String): ResponseEntity<UserResponse> {
        return try {
            val user = userService.getUser(id)
            val userResponse = userResponseMapper.apply(user)
            ResponseEntity.ok().body(userResponse)
        } catch (e: NoSuchElementException) {
            logger.error("getUserById $id", e)
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            logger.error("getUserById $id", e)
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/search/{$FIELD_NAME}")
    fun searchUserByName(
        @PathVariable(FIELD_NAME) name: String,
        @RequestParam(PAGE, defaultValue = DEFAULT_PAGE) page: Int,
        @RequestParam(SIZE, defaultValue = DEFAULT_PAGE_SIZE) size: Int
    ): ResponseEntity<List<UserResponse>> {
        return try {
            val users = userService.searchByName(name, page, size)
            val userResponseList = users.map(userResponseMapper::apply)
            ResponseEntity.ok().body(userResponseList)
        } catch (e: Exception) {
            logger.error("searchUserByName", e)
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping
    fun addUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        return try {
            val newUser = userMapper.apply(userRequest)
            val user = userService.add(newUser)
            val userResponse = userResponseMapper.apply(user)
            ResponseEntity.ok().body(userResponse)
        } catch (e: Exception) {
            logger.error("addUser", e)
            ResponseEntity.internalServerError().build()
        }
    }

    @PatchMapping("/{$FIELD_ID}")
    fun updateUser(
        @PathVariable(FIELD_ID) id: String,
        @RequestBody userRequest: UserRequest
    ): ResponseEntity<UserResponse> {
        return try {
            val updateUser = userMapper.apply(userRequest)
            val user = userService.update(id, updateUser)
            val userResponse = userResponseMapper.apply(user)
            ResponseEntity.ok().body(userResponse)
        } catch (e: Exception) {
            logger.error("updateUser", e)
            ResponseEntity.internalServerError().build()
        }
    }

    @DeleteMapping("/{$FIELD_ID}")
    fun deleteUser(@PathVariable(FIELD_ID) id: String): ResponseEntity<Any> {
        return try {
            userService.delete(id)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            logger.error("deleteUser", e)
            ResponseEntity.internalServerError().build()
        }
    }

    companion object {

        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val DEFAULT_PAGE = "0"
        private const val DEFAULT_PAGE_SIZE = "10"

        private const val FIELD_ID = "id"
        private const val FIELD_NAME = "name"
    }
}