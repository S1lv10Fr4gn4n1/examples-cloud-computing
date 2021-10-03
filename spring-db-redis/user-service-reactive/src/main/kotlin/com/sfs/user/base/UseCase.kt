package com.sfs.user.base

import reactor.core.publisher.Mono

interface UseCase<I, O> {

    fun build(param: I): Mono<O>
}