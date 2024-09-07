package com.stori.challenge.presentation.ui.intent

import com.stori.challenge.domain.model.RegistrationForm

sealed class RegistrationFormIntent {

    data class OnNameChanged(val name: String): RegistrationFormIntent()
    data class OnSurameChanged(val surname: String): RegistrationFormIntent()
    data class OnEmailChanged(val email: String): RegistrationFormIntent()
    data class OnPasswordChanged(val password: String): RegistrationFormIntent()
    data class OnConfirmPasswordChanged(val confirmPassword: String): RegistrationFormIntent()
    data class OnNextClicked(val form: RegistrationForm): RegistrationFormIntent()
    data object OnNavClicked: RegistrationFormIntent()
}