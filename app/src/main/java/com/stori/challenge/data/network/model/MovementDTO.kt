package com.stori.challenge.data.network.model

import com.google.firebase.Timestamp
import com.stori.challenge.domain.model.Movement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MovementDTO(
    val id: String = "",
    val userId: String = "",
    val date: Timestamp? = null,
    val amount: Float = 0F,
    val description: String = "",
    val state: String = "",
    val type: String = ""
) {
    fun toMovement(): Movement {
        return Movement(
            id = this.id,
            date = this.date?.toDate() ?: Date(),
            amount = this.amount,
            description = this.description,
            state = this.state,
            type = this.type
        )
    }
}