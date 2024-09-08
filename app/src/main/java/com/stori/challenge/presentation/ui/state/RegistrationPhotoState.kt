package com.stori.challenge.presentation.ui.state

import android.net.Uri

data class RegistrationPhotoState(
    val photo: Uri = Uri.EMPTY,
    val isLoading: Boolean = false,
    val showSuccess: Boolean = false
) {
    val isRegisterButtonEnabled: Boolean
        get() = photo.path?.isNotEmpty() == true
}