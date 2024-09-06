package com.stori.challenge.presentation.ui.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stori.challenge.presentation.ui.intent.RegistrationIntent
import com.stori.challenge.presentation.ui.viewmodel.RegistrationViewModel

@Composable
fun RegistrationFormScreen(
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    RegistrationFormScreenContent {
        viewModel.handleIntent(it)
    }
}

@Composable
fun RegistrationFormScreenContent(
    handleIntent: (RegistrationIntent) -> Unit
) {
    Column {
        Button(onClick = {
            handleIntent(RegistrationIntent.OnRegisterClicked(
                name = "Lucas 2",
                surname = "Miotti 2",
                email = "lucas.miotti+2@gmail.com",
                password = "Lucas19922!",
                photo = Uri.parse("asd")
        )
    ) }) {
            Text("Register")
        }
    }
}