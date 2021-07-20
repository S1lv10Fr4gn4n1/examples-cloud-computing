package com.example.aync

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import org.springframework.stereotype.Component

@Component
class AsyncFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        Thread.sleep(200)
        chain.doFilter(request, response)
    }
}