package com.stori.challenge.presentation.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.SignInUseCase
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.state.LoginState
import com.stori.extension.isValidEmail
import com.stori.extension.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state

    fun handleIntent(intent: LoginIntent) {
        if (intent is LoginIntent.OnLoginClicked) {
            validateFields(intent.email, intent.password)
        }
    }

    private fun validateFields(email: String, password: String) {
        val isEmailEmpty = email.isEmpty()
        val isEmailFormat = email.isValidEmail()
        val isPasswordEmpty = password.isEmpty()
        val isPasswordFormat = password.isValidPassword()

        if (listOf(isEmailEmpty, !isEmailFormat, isPasswordEmpty, !isPasswordFormat).any { it }) {
            _state.update {
                it.copy(
                    isEmailEmptyError = isEmailEmpty,
                    isEmailFormatError = !isEmailFormat,
                    isPasswordEmptyError = isPasswordEmpty,
                    isPasswordFormatError = !isPasswordFormat
                )
            }
        } else {
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            signInUseCase(email, password).collect {
                if (it is Resource.Success) _goToHomeScreen.emit(Unit)
            }
        }
    }
}