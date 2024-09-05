package com.stori.challenge.presentation.ui.intent

sealed class LoginIntent {
    object OnLoginClicked: LoginIntent()
    object OnRegisterClicked: LoginIntent()
}