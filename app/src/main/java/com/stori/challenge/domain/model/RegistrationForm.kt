package com.stori.challenge.domain.model

import android.net.Uri

data class RegistrationForm(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val photo: Uri? = null
)