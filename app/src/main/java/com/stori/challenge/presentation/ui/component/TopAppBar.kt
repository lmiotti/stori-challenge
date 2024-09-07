package com.stori.challenge.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import com.stori.challenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriTopBar(
    showNavButton: Boolean = false,
    onNavClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.shadow(1.dp),
        title = {
            Icon(
                painter = painterResource(id = R.drawable.ic_stori),
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        navigationIcon = {
            if (showNavButton) {
                IconButton(onClick = onNavClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    )
}