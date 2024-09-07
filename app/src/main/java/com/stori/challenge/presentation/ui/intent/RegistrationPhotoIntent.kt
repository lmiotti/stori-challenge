package com.stori.challenge.presentation.ui.intent

import android.net.Uri

sealed class RegistrationPhotoIntent {

    data object OnNavClicked: RegistrationPhotoIntent()
    data class OnTakePictureClicked(val photo: Uri): RegistrationPhotoIntent()
    data object OnRegisterClicked: RegistrationPhotoIntent()
}