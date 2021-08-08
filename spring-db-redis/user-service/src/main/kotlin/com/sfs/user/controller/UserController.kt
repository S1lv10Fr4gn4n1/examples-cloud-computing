package com.sfs.user.controller

import com.sfs.user.controller.mapper.UserResponseMapper
import com.sfs.user.controller.model.NewUserRequest
import com.sfs.user.controller.model.UserResponse
import com.sfs.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val userResponseMapper: UserResponseMapper
) {

    private val logger = LoggerFactory.getLogger(UserController::class.simpleName)

    // TODO SFS transform to reactive
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

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<UserResponse> {
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

    @GetMapping("/search/{name}")
    fun searchUserByName(
        @PathVariable("name") name: String,
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

//    @PostMapping
//    fun add(userRequest: NewUserRequest): ResponseEntity<UserResponse> {
//        userService.add()
//    }

    companion object {

        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val DEFAULT_PAGE = "0"
        private const val DEFAULT_PAGE_SIZE = "10"
    }
}