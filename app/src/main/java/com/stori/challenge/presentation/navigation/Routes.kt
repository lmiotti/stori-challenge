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
    data object RegistrationPhoto: Routes()

    @Serializable
    data object Home: Routes()
}