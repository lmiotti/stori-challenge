package com.stori.challenge.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.stori.challenge.R
import com.stori.challenge.presentation.ui.component.StoriButton
import com.stori.challenge.presentation.ui.component.StoriTextField
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
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
            is LoginIntent.OnRegisterClicked -> onRegisterClicked()
            else -> viewModel.handleIntent(intent)
        }
    }

    Scaffold(
        topBar = { StoriTopBar() }
    ) {
        LoginScreenContent(it, viewModel, handleIntent)
    }
}

@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = hiltViewModel(),
    handleIntent: (LoginIntent) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_validate_identity),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = stringResource(id = R.string.login_type_credentials),
            style = MaterialTheme.typography.bodyMedium
        )
        StoriTextField(
            modifier = Modifier.padding(top = 10.dp),
            labelId = R.string.auth_email,
            value = email,
            onValueChanged = {
                email = it
                handleIntent(LoginIntent.OnEmailChanged(it))
            },
            isError = state.isEmailError,
            errorId = R.string.auth_email_error
        )
        StoriTextField(
            modifier = Modifier.padding(top = 5.dp),
            labelId = R.string.auth_password,
            value = password,
            onValueChanged = {
                password = it
                handleIntent(LoginIntent.OnPasswordChanged(it))
            },
            isError = state.isPasswordError,
            isPasswordField = true,
            errorId = R.string.auth_password_error
        )
        StoriButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = { handleIntent(LoginIntent.OnLoginClicked(email, password)) },
            textId = R.string.login_login_button,
            enabled = state.isLoginButtonEnabled
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = stringResource(id = R.string.login_no_account)
        )
        StoriButton(
            onClick = { handleIntent(LoginIntent.OnRegisterClicked) },
            textId = R.string.login_register
        )
    }
}
