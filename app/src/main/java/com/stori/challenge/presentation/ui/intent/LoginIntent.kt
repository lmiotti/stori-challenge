package com.stori.challenge.presentation.ui.intent

sealed class LoginIntent {
    data class OnLoginClicked(val email: String, val password: String): LoginIntent()
    object OnRegisterClicked: LoginIntent()
}