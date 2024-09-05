package com.stori.challenge.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Login: Routes()
}