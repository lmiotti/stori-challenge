package com.stori.challenge.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.usecase.RegisterUseCase
import com.stori.challenge.presentation.ui.intent.RegistrationIntent
import com.stori.challenge.presentation.ui.state.LoginState
import com.stori.challenge.presentation.ui.state.RegistrationState
import com.stori.extension.isValidEmail
import com.stori.extension.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private var _goToPhotoScreen = MutableSharedFlow<Unit>()
    val goToPhotoScreen: SharedFlow<Unit>
        get() = _goToPhotoScreen

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState>
        get() = _state


    fun handleIntent(intent: RegistrationIntent) {
        if (intent is RegistrationIntent.OnNextClicked) {
            validateFields(intent.name, intent.surname, intent.email, intent.password, intent.confirmPassword)
        }
    }

    private fun validateFields(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val isNameEmpty = name.isEmpty()
        val isSurnameEmpty = surname.isEmpty()
        val isEmailEmpty = email.isEmpty()
        val isEmailFormat = email.isValidEmail()
        val isPasswordEmpty = password.isEmpty()
        val isPasswordFormat = password.isValidPassword()
        val isConfirmPasswordEmpty = confirmPassword.isEmpty()
        val isConfirmPasswordFormat = confirmPassword == password

        val isFormValid = !listOf(
            isNameEmpty,
            isSurnameEmpty,
            isEmailEmpty,
            !isEmailFormat,
            isPasswordEmpty,
            !isPasswordFormat,
            isConfirmPasswordEmpty,
            !isConfirmPasswordFormat
        ).any { it }

        if (isFormValid) {
            viewModelScope.launch { _goToPhotoScreen.emit(Unit) }
        } else {
            _state.update {
                it.copy(
                    isNameEmptyError = isNameEmpty,
                    isSurnameEmptyError = isSurnameEmpty,
                    isEmailEmptyError = isEmailEmpty,
                    isEmailFormatError = !isEmailFormat,
                    isPasswordEmptyError = isPasswordEmpty,
                    isPasswordFormatError = !isPasswordFormat,
                    isConfirmPasswordEmptyError = isConfirmPasswordEmpty,
                    isConfirmPasswordFormatError = !isConfirmPasswordFormat
                )
            }
        }
    }

    private fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        photo: Uri
    ) {
        viewModelScope.launch {
            registerUseCase(name, surname, email, password, photo).collect {
                // TODO: Handle response
            }
        }
    }
}