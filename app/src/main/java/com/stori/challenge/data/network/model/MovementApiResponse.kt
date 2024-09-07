package com.stori.challenge.data.network.model

import com.google.firebase.Timestamp
import com.stori.challenge.domain.model.Movement

data class MovementApiResponse(
    val date: Timestamp? = null,
    val amount: Long? = null,
    val description: String? = null
) {
    fun toMovement(): Movement = Movement(
        date = this.date?.toDate(),
        amount = this.amount,
        description = this.description
    )
}