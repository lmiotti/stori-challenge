package com.stori.challenge.presentation.ui.intent

sealed class LoginIntent {

    data class OnEmailChanged(val email: String): LoginIntent()
    data class OnPasswordChanged(val password: String): LoginIntent()

    data class OnLoginClicked(val email: String, val password: String): LoginIntent()
    object OnRegisterClicked: LoginIntent()
}