package com.sfs.user.util.logger

interface Logger {

    fun info(tag: String, message: String)

    fun error(tag: String, throwable: Throwable)

    fun error(tag: String, message: String, throwable: Throwable)
}