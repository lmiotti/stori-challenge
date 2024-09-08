package com.stori.challenge.presentation.ui.state

import com.stori.challenge.domain.model.Movement

data class HomeState(
    val isLoading: Boolean = false,
    val name: String = "",
    val movements: List<Movement> = listOf(),
    val balance: Float = 0F
)