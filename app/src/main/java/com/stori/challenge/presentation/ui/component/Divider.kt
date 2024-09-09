package com.stori.challenge.presentation.ui.component

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun StoriDivider() {
    Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
}