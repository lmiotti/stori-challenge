package com.stori.challenge.presentation.ui.state

import com.stori.challenge.extension.isEmailValid
import com.stori.challenge.extension.isPasswordValid

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
) {
    val isEmailError: Boolean
        get() {
            return email.isNotEmpty() && !email.isEmailValid()
        }

    val isPasswordError: Boolean
        get() {
            return password.isNotEmpty() && !password.isPasswordValid()
        }

    val isLoginButtonEnabled: Boolean
        get() {
           return email.isNotEmpty()
                   && email.isEmailValid()
                   && password.isNotEmpty()
                   && password.isPasswordValid()
        }
}