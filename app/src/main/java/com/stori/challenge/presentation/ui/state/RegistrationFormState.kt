package com.stori.challenge.presentation.ui.state

data class RegistrationFormState(
    val isNameEmpty: Boolean = true,
    val isSurnameEmpty: Boolean = true,
    val isEmailEmpty: Boolean = true,
    val isEmailError: Boolean = false,
    val isPasswordEmpty: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfirmPasswordEmpty: Boolean = false,
    val isConfirmPasswordError: Boolean = false
) {
    val isNextButtonEnabled: Boolean
        get() {
            return !isNameEmpty
                    && !isSurnameEmpty
                    && !isEmailEmpty
                    && !isEmailError
                    && !isPasswordEmpty
                    && !isPasswordError
                    && !isConfirmPasswordEmpty
                    && !isConfirmPasswordError
        }
}