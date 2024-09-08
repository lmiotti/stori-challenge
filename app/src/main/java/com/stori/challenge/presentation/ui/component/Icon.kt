package com.stori.challenge.presentation.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.stori.challenge.R

@Composable
fun StoriIcon() {
    Icon(
        modifier = Modifier.size(dimensionResource(id = R.dimen.splash_logo_size)),
        painter = painterResource(id = R.drawable.ic_stori),
        contentDescription = "Logo",
        tint = MaterialTheme.colorScheme.onPrimary
    )
}