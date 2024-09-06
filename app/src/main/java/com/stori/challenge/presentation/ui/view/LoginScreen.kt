package com.stori.challenge.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterClicked: () -> Unit
) {
    val handleIntent = { intent: LoginIntent ->
        when(intent) {
            is LoginIntent.OnLoginClicked -> viewModel.onLoginClicked(intent.email, intent.password)
            is LoginIntent.OnRegisterClicked -> onRegisterClicked()
        }
    }
    LoginScreenContent(handleIntent)
}

@Composable
fun LoginScreenContent(
    handleIntent: (LoginIntent) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = email, onValueChange = { email = it })
        TextField(value = password, onValueChange = { password = it })
        Button(
            onClick = { handleIntent(LoginIntent.OnLoginClicked(email, password)) }
        ) {
            Text("Log In")
        }
        Button(
            onClick = { handleIntent(LoginIntent.OnRegisterClicked) }
        ) {
            Text("Register")
        }
    }
}
