package com.sfs.user.base

interface Mapper<I, O> {

    fun apply(item: I): O
}