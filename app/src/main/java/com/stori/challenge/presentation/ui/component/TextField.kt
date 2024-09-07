package com.stori.challenge.presentation.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun StoriTextField(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean = false,
    isPasswordField: Boolean = false,
    @StringRes errorId: Int = 0
) {
    var isPasswordVisible: Boolean by remember { mutableStateOf(!isPasswordField) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(stringResource(id = labelId))
        },
        value = value,
        onValueChange = onValueChanged,
        isError = isError,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isPasswordField) {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (isPasswordVisible) "Hide password" else "Show password"
                    Icon(imageVector = image, description)
                }
            }
        },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (errorId == 0) "" else stringResource(id = errorId),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
    )
}