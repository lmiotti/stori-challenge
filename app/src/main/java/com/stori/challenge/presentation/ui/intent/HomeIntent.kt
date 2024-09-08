package com.stori.challenge.presentation.ui.intent

sealed class HomeIntent {

    data object OnSignOutClicked: HomeIntent()
}