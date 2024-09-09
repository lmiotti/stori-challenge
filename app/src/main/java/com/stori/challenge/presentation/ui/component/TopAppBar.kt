package com.stori.challenge.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.res.dimensionResource
import com.stori.challenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriTopBar(
    showNavButton: Boolean = false,
    showAction: Boolean = false,
    onNavClicked: () -> Unit = {},
    onActionClicked: () -> Unit = {},
    isHome: Boolean = false
) {
    TopAppBar(
        modifier = Modifier.shadow(dimensionResource(id = R.dimen.top_bar_shadow)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isHome)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Icon(
                painter = painterResource(id = R.drawable.ic_stori),
                contentDescription = "Logo",
                tint = if (isHome)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.secondary
            )
        },
        navigationIcon = {
            if (showNavButton) {
                IconButton(onClick = onNavClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = if (isHome)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        actions = {
            if (showAction) {
                IconButton(onClick = onActionClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Back",
                        tint = if (isHome)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    )
}