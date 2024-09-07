package com.stori.challenge.presentation.ui.intent

sealed class RegistrationFormIntent {

    data class OnNextClicked(
        val name: String,
        val surname: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ): RegistrationFormIntent()
}