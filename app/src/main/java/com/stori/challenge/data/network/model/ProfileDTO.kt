package com.stori.challenge.data.network.model

import com.stori.challenge.domain.model.Profile

data class ProfileDTO(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val balance: Long = 0L
) {
    fun toProfile(): Profile = Profile(name = this.name)
}