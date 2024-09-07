package com.stori.challenge.presentation.ui.state

data class LoginState(
    val isLoading: Boolean = false,
    val isEmailEmpty: Boolean = true,
    val isEmailError: Boolean = false,
    val isPasswordEmpty: Boolean = true,
    val isPasswordError: Boolean = false,
) {
    val isLoginButtonEnabled: Boolean
        get() {
           return !isEmailEmpty
                   && !isEmailError
                   && !isPasswordEmpty
                   && !isPasswordError
        }
}