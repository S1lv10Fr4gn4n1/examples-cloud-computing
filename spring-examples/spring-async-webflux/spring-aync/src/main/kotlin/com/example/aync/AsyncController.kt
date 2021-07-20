package com.example.aync

import java.util.concurrent.CompletableFuture
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AsyncController {

    @GetMapping("/async_result")
    @Async
    fun getResultAsync(): CompletableFuture<String> {
        Thread.sleep(500)
        return CompletableFuture.completedFuture("Result is ready")
    }
}