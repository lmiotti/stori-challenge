package com.stori.challenge.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.RegisterUseCase
import com.stori.challenge.presentation.ui.intent.RegistrationIntent
import com.stori.challenge.presentation.ui.state.LoginState
import com.stori.challenge.presentation.ui.state.RegistrationState
import com.stori.challenge.extension.isValidEmail
import com.stori.challenge.extension.isValidPassword
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

    private var _goToPhotoScreen = MutableSharedFlow<RegistrationForm>()
    val goToPhotoScreen: SharedFlow<RegistrationForm>
        get() = _goToPhotoScreen

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen


    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState>
        get() = _state


    fun handleIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.OnNextClicked ->
                validateFields(intent.name, intent.surname, intent.email, intent.password, intent.confirmPassword)
            is RegistrationIntent.OnRegisterClicked -> register(intent.form)
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
            viewModelScope.launch {
                _goToPhotoScreen.emit(
                    RegistrationForm(name = name, surname = surname, email = email, password)
                )
            }
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

    private fun register(form: RegistrationForm) {
        viewModelScope.launch {
            registerUseCase(form).collect {
                if (it is Resource.Success) _goToHomeScreen.emit(Unit)
            }
        }
    }
}