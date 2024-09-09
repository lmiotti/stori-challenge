package com.stori.challenge.presentation.navigation

import com.stori.challenge.domain.model.Movement
import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Splash: Routes()

    @Serializable
    data object Login: Routes()

    @Serializable
    data object RegistrationForm: Routes()

    @Serializable
    data class RegistrationPhoto(
        val name: String,
        val surname: String,
        val email: String,
        val password: String
    ): Routes()

    @Serializable
    data object Home: Routes()

    @Serializable
    data class MovementDetails(
        val id: String,
        val date: String,
        val amount: Float,
        val description: String,
        val state: String,
        val type: String
    ): Routes()
}