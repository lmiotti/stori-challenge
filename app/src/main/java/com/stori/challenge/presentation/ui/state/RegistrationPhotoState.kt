package com.stori.challenge.presentation.ui.state

import android.net.Uri

data class RegistrationPhotoState(
    val photo: Uri = Uri.EMPTY
) {
    val isRegisterButtonEnabled: Boolean
        get() = photo.path?.isNotEmpty() == true
}