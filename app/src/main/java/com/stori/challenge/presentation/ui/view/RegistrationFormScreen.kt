package com.stori.challenge.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stori.challenge.R
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.presentation.ui.component.StoriButton
import com.stori.challenge.presentation.ui.component.StoriTextField
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import com.stori.challenge.presentation.ui.viewmodel.RegistrationFormViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegistrationFormScreen(
    viewModel: RegistrationFormViewModel = hiltViewModel(),
    goToPhotoScreen: (RegistrationForm) -> Unit,
    goBack: () -> Unit
) {
    val handleIntent = { intent: RegistrationFormIntent ->
        when(intent) {
            is RegistrationFormIntent.OnNavClicked -> goBack()
            is RegistrationFormIntent.OnNextClicked -> goToPhotoScreen(intent.form)
            else -> viewModel.handleIntent(intent)
        }
    }

    Scaffold(
        topBar = {
            StoriTopBar(
                showNavButton = true,
                onNavClicked = { handleIntent(RegistrationFormIntent.OnNavClicked) }
            )
        }
    ) {
        RegistrationFormScreenContent(
            it,
            handleIntent = handleIntent,
            viewModel.state
        )
    }

}

@Composable
fun RegistrationFormScreenContent(
    paddingValues: PaddingValues,
    handleIntent: (RegistrationFormIntent) -> Unit,
    stateFlow: StateFlow<RegistrationFormState>
) {
    val state by stateFlow.collectAsStateWithLifecycle()

    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.registration_form_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = stringResource(id = R.string.registration_form_description),
            style = MaterialTheme.typography.bodyMedium
        )
        StoriTextField(
            modifier = Modifier.padding(top = 10.dp),
            labelId = R.string.registration_form_name,
            value = name,
            onValueChanged = {
                name = it
                handleIntent(RegistrationFormIntent.OnNameChanged(name))
            }
        )
        StoriTextField(
            modifier = Modifier.padding(top = 5.dp),
            labelId = R.string.registration_form_surname,
            value = surname,
            onValueChanged = {
                surname = it
                handleIntent(RegistrationFormIntent.OnSurameChanged(surname))
            }
        )
        StoriTextField(
            modifier = Modifier.padding(top = 5.dp),
            labelId = R.string.auth_email,
            value = email,
            onValueChanged = {
                email = it
                handleIntent(RegistrationFormIntent.OnEmailChanged(email))
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
                handleIntent(RegistrationFormIntent.OnPasswordChanged(password, confirmPassword))
            },
            isError = state.isPasswordError,
            errorId = R.string.auth_password_error,
            isPasswordField = true
        )
        StoriTextField(
            modifier = Modifier.padding(top = 5.dp),
            labelId = R.string.registration_form_confirm_password,
            value = confirmPassword,
            onValueChanged = {
                confirmPassword = it
                handleIntent(RegistrationFormIntent.OnConfirmPasswordChanged(password, confirmPassword))
            },
            isError = state.isConfirmPasswordError,
            errorId = R.string.registration_confirm_password_error,
            isPasswordField = true
        )
        Spacer(modifier = Modifier.weight(1f))
        StoriButton(
            onClick = {
                val form = RegistrationForm(name = name, surname = surname, email = email, password)
                handleIntent(RegistrationFormIntent.OnNextClicked(form))
            },
            textId = R.string.registration_next_button,
            enabled = state.isNextButtonEnabled
        )
    }
}