package com.stori.challenge.presentation.navigation

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
    data object MovementDetails: Routes()
}