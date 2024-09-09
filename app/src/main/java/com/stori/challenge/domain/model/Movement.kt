package com.stori.challenge.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Movement(
    val id: String,
    val date: Date,
    val amount: Float,
    val description: String,
    val state: String,
    val type: String
) {
    val formattedDate: String
        get() {
            val sfd = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            return sfd.format(this.date)
        }

    companion object {
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }
}