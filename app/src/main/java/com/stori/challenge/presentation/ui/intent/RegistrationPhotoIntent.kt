package com.stori.challenge.presentation.ui.intent

import android.net.Uri

sealed class RegistrationPhotoIntent {

    data object OnNavClicked: RegistrationPhotoIntent()
    data object OnTakePictureClicked: RegistrationPhotoIntent()
    data class OnRegisterClicked(
        val photo: Uri
    ): RegistrationPhotoIntent()
}