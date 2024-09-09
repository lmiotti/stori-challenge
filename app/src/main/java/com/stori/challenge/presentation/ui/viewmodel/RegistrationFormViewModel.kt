package com.stori.challenge.presentation.ui.viewmodel

import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationFormViewModel @Inject constructor(): BaseViewModel<RegistrationFormState, RegistrationFormIntent>(
    initialState = RegistrationFormState()
) {

    override fun handleIntent(intent: RegistrationFormIntent) {
        when (intent) {
            is RegistrationFormIntent.OnNameChanged ->
                _state.update { it.copy(name = intent.name) }
            is RegistrationFormIntent.OnSurameChanged ->
                _state.update { it.copy(surname = intent.surname) }
            is RegistrationFormIntent.OnEmailChanged ->
                _state.update { it.copy(email = intent.email) }
            is RegistrationFormIntent.OnPasswordChanged ->
                _state.update { it.copy(password = intent.password) }
            is RegistrationFormIntent.OnConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = intent.confirmPassword) }
            else -> {}
        }
    }
}