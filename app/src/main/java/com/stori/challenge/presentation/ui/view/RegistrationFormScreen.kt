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
import androidx.compose.ui.res.dimensionResource
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
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        RegistrationFormScreenContent(it, state, handleIntent)
    }

}

@Composable
fun RegistrationFormScreenContent(
    paddingValues: PaddingValues,
    state: RegistrationFormState,
    handleIntent: (RegistrationFormIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xl))
            .padding(top = dimensionResource(id = R.dimen.padding_xl))
    ) {
        Text(
            text = stringResource(id = R.string.registration_form_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_l)),
            text = stringResource(id = R.string.registration_form_description),
            style = MaterialTheme.typography.bodyMedium
        )
        StoriTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_m)),
            labelId = R.string.registration_form_name,
            value = state.name,
            onValueChanged = { handleIntent(RegistrationFormIntent.OnNameChanged(it)) }
        )
        StoriTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_s)),
            labelId = R.string.registration_form_surname,
            value = state.surname,
            onValueChanged = { handleIntent(RegistrationFormIntent.OnSurameChanged(it))
            }
        )
        StoriTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_s)),
            labelId = R.string.auth_email,
            value = state.email,
            onValueChanged = { handleIntent(RegistrationFormIntent.OnEmailChanged(it)) },
            isError = state.isEmailError,
            errorId = R.string.auth_email_error
        )
        StoriTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_s)),
            labelId = R.string.auth_password,
            value = state.password,
            onValueChanged = { handleIntent(RegistrationFormIntent.OnPasswordChanged(it))
            },
            isError = state.isPasswordError,
            errorId = R.string.auth_password_error,
            isPasswordField = true
        )
        StoriTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_s)),
            labelId = R.string.registration_form_confirm_password,
            value = state.confirmPassword,
            onValueChanged = { handleIntent(RegistrationFormIntent.OnConfirmPasswordChanged(it)) },
            isError = state.isConfirmPasswordError,
            errorId = R.string.registration_confirm_password_error,
            isPasswordField = true
        )
        Spacer(modifier = Modifier.weight(1f))
        StoriButton(
            onClick = {
                val form = RegistrationForm(
                    name = state.name,
                    surname = state.surname,
                    email = state.email,
                    password = state.password
                )
                handleIntent(RegistrationFormIntent.OnNextClicked(form))
            },
            textId = R.string.registration_next_button,
            enabled = state.isNextButtonEnabled
        )
    }
}