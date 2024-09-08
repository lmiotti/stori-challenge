package com.stori.challenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.stori.challenge.R
import com.stori.challenge.domain.model.Movement
import java.util.Date

@Composable
fun MovementItem(
    movement: Movement,
    onMovementClicked: (Movement) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.home_movement_height))
            .clickable { onMovementClicked(movement) }
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xs)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = movement.description,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_xs)),
                    text = movement.date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = String.format(
                    stringResource(id = R.string.home_money_format),
                    movement.amount
                ),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
    }
}

@Preview
@Composable
fun MovementItemPreview() {
    val movement = Movement(
        date = "01/01/00",
        amount = 10000F,
        description = "Cashback"
    )
    MovementItem(movement, {})
}