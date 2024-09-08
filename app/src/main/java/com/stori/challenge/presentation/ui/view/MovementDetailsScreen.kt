package com.stori.challenge.presentation.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.stori.challenge.presentation.ui.component.StoriTopBar

@Composable
fun MovementDetailsScreen(
    goBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            StoriTopBar(
                showNavButton = true,
                onNavClicked = goBackClicked
            )
        }
    ) {
        MovementDetailsScreenContent(it)
    }
}

@Composable
fun MovementDetailsScreenContent(
    paddingValues: PaddingValues
) {

}