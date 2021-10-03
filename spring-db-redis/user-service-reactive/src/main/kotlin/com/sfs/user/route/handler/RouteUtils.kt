package com.sfs.user.route.handler

import com.sfs.user.route.Constants
import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.getPageAndSize(): Pair<Int, Int> {
    with(queryParams().toSingleValueMap()) {
        val page = this[Constants.PAGE]?.toInt() ?: Constants.DEFAULT_PAGE
        val size = this[Constants.SIZE]?.toInt() ?: Constants.DEFAULT_PAGE_SIZE
        return Pair(page, size)
    }
}

