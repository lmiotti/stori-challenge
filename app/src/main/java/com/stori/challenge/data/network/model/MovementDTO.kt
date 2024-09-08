package com.stori.challenge.data.network.model

import com.google.firebase.Timestamp
import com.stori.challenge.domain.model.Movement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MovementDTO(
    val userId: String = "",
    val date: Timestamp? = null,
    val amount: Float = 0F,
    val description: String = ""
) {
    fun toMovement(): Movement {
        return Movement(
            date = date?.toDate() ?: Date(),
            amount = this.amount,
            description = this.description
        )
    }
}