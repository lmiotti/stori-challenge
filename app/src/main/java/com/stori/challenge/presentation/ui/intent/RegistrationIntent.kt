package com.stori.challenge.presentation.ui.intent

import android.net.Uri
import com.stori.challenge.domain.model.RegistrationForm

sealed class RegistrationIntent {

    data class OnNextClicked(
        val name: String,
        val surname: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ): RegistrationIntent()

    data class OnRegisterClicked(
        val form: RegistrationForm
    ): RegistrationIntent()
}