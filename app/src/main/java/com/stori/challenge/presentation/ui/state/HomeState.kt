package com.stori.challenge.presentation.ui.state

import android.net.Uri
import com.stori.challenge.domain.model.Movement

data class HomeState(
    val isLoading: Boolean = false,
    val name: String = "",
    val photo: Uri? = null,
    val movements: List<Movement> = listOf()
)