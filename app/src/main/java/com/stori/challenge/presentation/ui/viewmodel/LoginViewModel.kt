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
            is LoginIntent.OnEmailChanged -> _state.update { it.copy(email = intent.email) }
            is LoginIntent.OnPasswordChanged -> _state.update { it.copy(password = intent.password) }
            is LoginIntent.OnLoginClicked -> login()
            else -> {}
        }
    }

    private fun login() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            signInUseCase(_state.value.email, _state.value.password).collect {
                if (it is Resource.Success) _goToHomeScreen.emit(Unit)
            }
        }
    }
}