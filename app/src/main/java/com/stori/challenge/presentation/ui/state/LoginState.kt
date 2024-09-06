package com.stori.challenge.presentation.ui.state

data class LoginState(
    val isLoading: Boolean = false,
    val isEmailEmptyError: Boolean = false,
    val isEmailFormatError: Boolean = false,
    val isPasswordEmptyError: Boolean = false,
    val isPasswordFormatError: Boolean = false
)