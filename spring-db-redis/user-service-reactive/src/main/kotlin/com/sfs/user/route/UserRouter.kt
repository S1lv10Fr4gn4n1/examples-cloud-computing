package com.sfs.user.route

import com.sfs.user.route.Constants.FIELD_ID
import com.sfs.user.route.Constants.FIELD_NAME
import com.sfs.user.route.handler.AddUserHandler
import com.sfs.user.route.handler.DeleteUserHandler
import com.sfs.user.route.handler.GetUserByIdHandler
import com.sfs.user.route.handler.GetUsersHandler
import com.sfs.user.route.handler.SearchUserByNameHandler
import com.sfs.user.route.handler.UpdateUserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class UserRouter {

    @Bean
    fun route(
        addUserHandler: AddUserHandler,
        deleteUserHandler: DeleteUserHandler,
        getUserByIdHandler: GetUserByIdHandler,
        getUsersHandler: GetUsersHandler,
        searchUserByNameHandler: SearchUserByNameHandler,
        updateUserHandler: UpdateUserHandler
    ): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route()
            .GET("/user", defaultPredicate(), getUsersHandler::handle)
            .GET("/user/{$FIELD_ID}", defaultPredicate(), getUserByIdHandler::handle)
            .GET("/user/search/{$FIELD_NAME}", defaultPredicate(), searchUserByNameHandler::handle)
            .POST("/user", defaultPredicate(), addUserHandler::handle)
            .PATCH("/user/{$FIELD_ID}", defaultPredicate(), updateUserHandler::handle)
            .DELETE("/user/{$FIELD_ID}", defaultPredicate(), deleteUserHandler::handle)
            .build()
    }

    private fun defaultPredicate(): RequestPredicate {
        return accept(MediaType.APPLICATION_JSON)
    }
}


