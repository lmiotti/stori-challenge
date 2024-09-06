package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.SignInUseCase
import com.stori.challenge.presentation.ui.intent.LoginIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen



    fun handleIntent(intent: LoginIntent) {
        if (intent is LoginIntent.OnLoginClicked) {
            login(intent.email, intent.password)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).collect {
                if (it is Resource.Success) _goToHomeScreen.emit(Unit)
            }
        }
    }


}