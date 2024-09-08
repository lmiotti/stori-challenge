package com.stori.challenge.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Movement(
    val date: Date,
    val amount: Float?,
    val description: String
) {
    val formattedDate: String
        get() {
            val sfd = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return sfd.format(this.date)
        }
}