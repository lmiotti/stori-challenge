package com.stori.challenge.presentation.ui.intent

import com.stori.challenge.domain.model.Movement

sealed class HomeIntent {

    data class OnMovementClicked(val movement: Movement): HomeIntent()
    data object OnSignOutClicked: HomeIntent()
}