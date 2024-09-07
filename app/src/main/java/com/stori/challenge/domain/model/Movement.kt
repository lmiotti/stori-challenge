package com.stori.challenge.domain.model

import java.util.Date

data class Movement(
    val date: Date?,
    val amount: Long?,
    val description: String?
)