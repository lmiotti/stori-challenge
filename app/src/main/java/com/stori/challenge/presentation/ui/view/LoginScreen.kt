package com.stori.challenge.presentation.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterClicked: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToHomeScreen.collectLatest {
                goToHomeScreen()
            }
        }
    }

    val handleIntent = { intent: LoginIntent ->
        when(intent) {
            is LoginIntent.OnLoginClicked -> viewModel.handleIntent(intent)
            is LoginIntent.OnRegisterClicked -> onRegisterClicked()
        }
    }
    LoginScreenContent(viewModel, handleIntent)
}

@Composable
fun LoginScreenContent(
    viewModel: AuthViewModel = hiltViewModel(),
    handleIntent: (LoginIntent) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(state) {
        val text = when {
            state.isEmailEmptyError -> "Email Empty"
            state.isEmailFormatError -> "Email Format"
            state.isPasswordEmptyError -> "Password Empty"
            state.isPasswordFormatError -> "Password format"
            else -> ""
        }
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

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
