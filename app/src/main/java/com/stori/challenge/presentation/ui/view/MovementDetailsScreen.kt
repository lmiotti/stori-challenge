package com.stori.challenge.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stori.challenge.R
import com.stori.challenge.presentation.ui.component.StoriDivider
import com.stori.challenge.presentation.ui.component.StoriTopBar

@Composable
fun MovementDetailsScreen(
    id: String,
    date: String,
    amount: Float,
    description: String,
    state: String,
    type: String,
    goBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            StoriTopBar(
                showNavButton = true,
                onNavClicked = goBackClicked,
                isHome = true
            )
        }
    ) {
        MovementDetailsScreenContent(
            paddingValues = it,
            id = id,
            date = date,
            amount = amount,
            description = description,
            state = state,
            type = type
        )
    }
}

@Composable
fun MovementDetailsScreenContent(
    paddingValues: PaddingValues,
    id: String,
    date: String,
    amount: Float,
    description: String,
    state: String,
    type: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = dimensionResource(id = R.dimen.padding_xl)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.home_movement_detail_logo_size)),
            painter = painterResource(id = R.drawable.ic_expense),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_l)))
        Text(
            text = String.format(stringResource(id = R.string.home_money_format), amount),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xxl)))
        StoriDivider()
        MovementDetailItem(key = "Reference id", value = id)
        MovementDetailItem(
            key = "Amount",
            value = String.format(stringResource(id = R.string.home_money_format), amount)
        )
        MovementDetailItem(key = "Date", value = date)
        MovementDetailItem(key = "Description", value = description)
        MovementDetailItem(key = "State", value = state)
        MovementDetailItem(key = "Type", value = type)
    }
}

@Composable
fun MovementDetailItem(
    key: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xl))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_l)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        StoriDivider()
    }
}