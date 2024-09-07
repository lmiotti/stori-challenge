package com.stori.challenge.presentation.ui.state

import com.stori.challenge.extension.isEmailValid
import com.stori.challenge.extension.isPasswordValid

data class RegistrationFormState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
) {
    val isEmailError: Boolean
        get() = email.isNotEmpty() && !email.isEmailValid()

    val isPasswordError: Boolean
        get() = password.isNotEmpty() && !password.isPasswordValid()

    val isConfirmPasswordError: Boolean
        get() = confirmPassword.isNotEmpty() && password != confirmPassword

    val isNextButtonEnabled: Boolean
        get() {
            return name.isNotEmpty()
                    && surname.isNotEmpty()
                    && email.isNotEmpty()
                    && !isEmailError
                    && password.isNotEmpty()
                    && !isPasswordError
                    && confirmPassword.isNotEmpty()
                    && !isConfirmPasswordError
        }
}