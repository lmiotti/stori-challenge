package com.stori.challenge.presentation.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.stori.challenge.R

@Composable
fun StoriButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textId: Int,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.button_height)),
        onClick = onClick,
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.button_progress_size))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_m)))
                Text(text = stringResource(id = R.string.loading))
            }
        } else {
            Text(stringResource(id = textId))
        }
    }
}