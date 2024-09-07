package com.stori.challenge.presentation.ui.intent

sealed class LoginIntent {

    data class OnEmailChanged(val email: String): LoginIntent()
    data class OnPasswordChanged(val password: String): LoginIntent()

    data object OnLoginClicked: LoginIntent()
    data object OnRegisterClicked: LoginIntent()
}