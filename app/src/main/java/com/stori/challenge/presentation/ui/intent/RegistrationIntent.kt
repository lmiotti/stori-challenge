package com.stori.challenge.presentation.ui.intent

import android.net.Uri

sealed class RegistrationIntent {

    data class OnRegisterClicked(
        val name: String,
        val surname: String,
        val email: String,
        val password: String,
        val photo: Uri
    ): RegistrationIntent()
}