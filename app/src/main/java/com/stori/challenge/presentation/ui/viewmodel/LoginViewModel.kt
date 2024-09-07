package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.SignInUseCase
import com.stori.challenge.extension.isEmailValid
import com.stori.challenge.extension.isPasswordValid
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnEmailChanged -> validateEmail(intent.email)
            is LoginIntent.OnPasswordChanged -> validatePassword(intent.password)
            is LoginIntent.OnLoginClicked -> login(intent.email, intent.password)
            else -> {}
        }
    }

    private fun validateEmail(email: String) {
        _state.update {
            it.copy(
                isEmailEmpty = email.isEmpty(),
                isEmailError = email.isNotEmpty() && !email.isEmailValid()
            )
        }
    }

    private fun validatePassword(password: String) {
        _state.update {
            it.copy(
                isPasswordEmpty = password.isEmpty(),
                isPasswordError = password.isNotEmpty() && !password.isPasswordValid()
            )
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