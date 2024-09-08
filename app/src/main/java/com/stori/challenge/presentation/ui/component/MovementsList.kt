package com.stori.challenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.stori.challenge.R
import com.stori.challenge.domain.model.Movement

@Composable
fun MovementsList(
    movements: List<Movement>,
    onMovementClicked: (Movement) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            stringResource(id = R.string.home_movements),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.padding_m)),
        )
        movements.take(5).forEach {
            MovementItem(
                movement = it,
                onMovementClicked = { onMovementClicked(it) }
            )
        }
    }

}