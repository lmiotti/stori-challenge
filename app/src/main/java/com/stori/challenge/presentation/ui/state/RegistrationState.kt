package com.stori.challenge.presentation.ui.state

data class RegistrationState(
    val isLoading: Boolean = false,
    val isNameEmptyError: Boolean = false,
    val isSurnameEmptyError: Boolean = false,
    val isEmailEmptyError: Boolean = false,
    val isEmailFormatError: Boolean = false,
    val isPasswordEmptyError: Boolean = false,
    val isPasswordFormatError: Boolean = false,
    val isConfirmPasswordEmptyError: Boolean = false,
    val isConfirmPasswordFormatError: Boolean = false
)