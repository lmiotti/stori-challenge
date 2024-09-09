package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.SignInUseCase
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
): BaseViewModel<LoginState, LoginIntent>(
    initialState = LoginState()
) {

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen

    private var _showError = MutableSharedFlow<String>()
    val showError: SharedFlow<String>
        get() = _showError

    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnEmailChanged ->
                _state.update { it.copy(email = intent.email) }
            is LoginIntent.OnPasswordChanged ->
                _state.update { it.copy(password = intent.password) }
            is LoginIntent.OnLoginClicked ->
                login()
            else -> {}
        }
    }

    private fun login() {
        viewModelScope.launch {
            val signIn = signInUseCase(_state.value.email, _state.value.password)
            signIn.collect {
                when(it) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _goToHomeScreen.emit(Unit)
                    }
                    is Resource.Failure -> {
                        _state.update { it.copy(isLoading = false) }
                        _showError.emit(it.error ?: "")
                    }
                }
            }
        }
    }
}