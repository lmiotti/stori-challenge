package com.stori.challenge.presentation.ui.state

import com.stori.challenge.extension.isEmailValid
import com.stori.challenge.extension.isPasswordValid

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
) {
    val isEmailError: Boolean
        get() = email.isNotEmpty() && !email.isEmailValid()

    val isPasswordError: Boolean
        get() = password.isNotEmpty() && !password.isPasswordValid()

    val isLoginButtonEnabled: Boolean
        get() = email.isNotEmpty()
                   && !isEmailError
                   && password.isNotEmpty()
                   && !isPasswordError
}