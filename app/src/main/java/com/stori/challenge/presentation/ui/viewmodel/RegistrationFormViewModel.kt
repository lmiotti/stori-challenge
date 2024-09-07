package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.extension.isEmailValid
import com.stori.challenge.extension.isPasswordValid
import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFormViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(RegistrationFormState())
    val state: StateFlow<RegistrationFormState>
        get() = _state


    fun handleIntent(intent: RegistrationFormIntent) {
        when (intent) {
            is RegistrationFormIntent.OnNameChanged -> validateName(intent.name)
            is RegistrationFormIntent.OnSurameChanged -> validateSurname(intent.surname)
            is RegistrationFormIntent.OnEmailChanged -> validateEmail(intent.email)
            is RegistrationFormIntent.OnPasswordChanged -> validatePassword(intent.password, intent.confirmPassword)
            is RegistrationFormIntent.OnConfirmPasswordChanged -> validateConfirmPassword(intent.password, intent.confirmPassword)
            else -> {}
        }
    }

    private fun validateName(name: String) {
        _state.update { it.copy(isNameEmpty = name.isEmpty()) }
    }

    private fun validateSurname(surname: String) {
        _state.update { it.copy(isSurnameEmpty = surname.isEmpty()) }
    }

    private fun validateEmail(email: String) {
        _state.update {
            it.copy(
                isEmailEmpty = email.isEmpty(),
                isEmailError = email.isNotEmpty() && !email.isEmailValid()
            )
        }
    }

    private fun validatePassword(password: String, confirmPassword: String) {
        _state.update {
            it.copy(
                isPasswordEmpty = password.isEmpty(),
                isPasswordError = password.isNotEmpty() && !password.isPasswordValid(),
                isConfirmPasswordError = password != confirmPassword
            )
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String) {
        _state.update {
            it.copy(
                isConfirmPasswordEmpty = password.isEmpty(),
                isConfirmPasswordError = password != confirmPassword
            )
        }
    }
}