package com.stori.challenge.presentation.ui.intent

import android.net.Uri

sealed class RegistrationPhotoIntent {
    data class OnRegisterClicked(
        val photo: Uri
    ): RegistrationPhotoIntent()
}