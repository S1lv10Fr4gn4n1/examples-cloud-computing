package com.sfs.user.service.id

import org.springframework.stereotype.Component

@Component
class IdGenerator(
    private val uuIdHelper: UUIDHelper
) {

    fun getId(string: String): String {
        return uuIdHelper.uuid3(UUIDHelper.Namespace.OID, string).toString()
    }
}