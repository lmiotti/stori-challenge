package com.stori.challenge.data.network.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(): SessionManager {

    override var userId: String = ""

    override fun clean() {
        userId = ""
    }
}

interface SessionManager {

    var userId: String
    fun clean()
}